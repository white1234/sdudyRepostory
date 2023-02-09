package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.mapper.SysLogMapper;
import com.studydemo.demo.model.entity.SysLog;
import com.studydemo.demo.service.ISysLogService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/3 22:41
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    private static final Log logger = LogFactory.getLog(LoginServiceImpl.class);

    @Override
    @Async("asyncThreadPoolExecutor")
    public void saveLogAsync(SysLog log) {
        try {
            this.save(log);
            logger.info("异步方法内部线程名称：{}"+Thread.currentThread().getName());
        }catch (Exception e){
            throw new BaseException("500","系统日志异步存储错误!");
        }
    }
}
