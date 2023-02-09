package com.studydemo.demo.task;

import com.studydemo.demo.model.LogQueue;
import com.studydemo.demo.model.entity.SysLog;
import com.studydemo.demo.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 11:18
 */
@Slf4j
@Component
public class SysLogTask {

    @Resource
    LogQueue logQueue;

    @Resource
    ISysLogService sysLogService;

    private volatile List<SysLog> operLogs = Collections.synchronizedList(new ArrayList<>());

    /*
     * @Description 定时插入操作日志到数据库
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/8 11:39
     */
    //@Scheduled(cron = "0 */2 * * * ?")
    public void logFixDelay(){
        log.info("================>进入定时 插入操作日志 任务");
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
                sysLogService.saveBatch(operLogs);
            }catch (Exception e){
                log.info("批量插入任务执行异常"+e.getMessage());
            }finally {
                operLogs.clear();
            }

        }
    }
}
