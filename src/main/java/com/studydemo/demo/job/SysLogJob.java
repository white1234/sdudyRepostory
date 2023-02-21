package com.studydemo.demo.job;

import cn.hutool.extra.spring.SpringUtil;
import com.studydemo.demo.model.LogQueue;
import com.studydemo.demo.model.entity.SysLog;
import com.studydemo.demo.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 22:39
 */
@Slf4j
public class SysLogJob implements Job {

    @Resource
    LogQueue logQueue;

    @Resource
    ISysLogService sysLogService;

    private volatile List<SysLog> operLogs = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("job方式 ================>进入定时 插入操作日志 任务");
        //LogQueue logQueue = SpringUtil.getBean("logQueue");
        //ISysLogService sysLogService = SpringUtil.getBean("sysLogService");
        while (true){
            SysLog sysLog = logQueue.poll();
            if(null == sysLog){
                break;
            }
            operLogs.add(sysLog);
        }
        if(operLogs.size()>0) {
            try {
                log.info("================>批量存储日志到数据库，日志大小"+operLogs.size());
                sysLogService.batchSave(operLogs);
            }catch (Exception e){
                log.info("批量插入任务执行异常"+e.getMessage());
            }finally {
                operLogs.clear();
            }

        }
    }
}
