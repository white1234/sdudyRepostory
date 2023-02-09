package com.studydemo.demo.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.pagehelper.PageInfo;
import com.studydemo.demo.model.entity.JobAndTriggerDto;
import com.studydemo.demo.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 19:58
 */
@Slf4j
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzService quartzService = SpringUtil.getBean("quartzService");
        PageInfo<JobAndTriggerDto> jobAndTriggerDtoPageInfo = quartzService.getJobAndTriggerDetails(1,10);
        log.info("任务列表总数为："+jobAndTriggerDtoPageInfo.getTotal());
        log.info("hello job 执行时间为"+ DateUtil.formatDateTime(new Date()));
    }
}
