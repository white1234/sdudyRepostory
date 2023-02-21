package com.studydemo.demo.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
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

        //将quartz中的job对象自动注入到spring中，使job对象可使用spring注解@Autowird
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
