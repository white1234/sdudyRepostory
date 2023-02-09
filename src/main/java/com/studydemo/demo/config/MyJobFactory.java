package com.studydemo.demo.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 23:44
 */
@Component
public class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected  Object createJobInstance(TriggerFiredBundle bundle) throws Exception{

        //调用父类的创建实例方法
        Object jobInstance = super.createJobInstance(bundle);

        //
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
