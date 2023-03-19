package com.studydemo.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.mapper.JobDetailMapper;
import com.studydemo.demo.model.entity.JobAndTriggerDto;
import com.studydemo.demo.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 15:46
 */
@Slf4j
@Service("quartzService")
@CacheConfig(cacheNames = "quartz",keyGenerator = "keyGenerator")
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private JobDetailMapper jobDetailMapper;

    @Autowired
    private Scheduler scheduler;


    @Cacheable(value = "user")
    @Override
    public PageInfo<JobAndTriggerDto> getJobAndTriggerDetails(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<JobAndTriggerDto> list = jobDetailMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTriggerDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * @Description 新增定时任务
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/8 16:52
     */
    @Override
    public void addjob(String jName, String jGroup, String tName, String tGroup, String cron) {
        try {
            Class cla = Class.forName("com.studydemo.demo.job.".concat(jName));
            //构建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(cla).withIdentity(jName,jGroup).build();
            //用cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(tName,tGroup)
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new BaseException("500","创建定时任务失败");
        }
    }

    @Override
    public void pausejob(String jName, String jGroupe) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jName,jGroupe));
    }

    @Override
    public void resumejob(String jName, String jGroup) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jName,jGroup));
    }

    @Override
    public void rescheduleJob(String jName, String jGroup, String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jName,jGroup);
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.rescheduleJob(triggerKey,trigger);
    }

    @Override
    public void deletejob(String jName, String jGroup) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jName,jGroup));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jName,jGroup));
        scheduler.deleteJob(JobKey.jobKey(jName,jGroup));
    }
}
