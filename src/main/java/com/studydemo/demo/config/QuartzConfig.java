package com.studydemo.demo.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/8 15:04
 */
@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    @Resource
    MyJobFactory myJobFactory;

    @Bean
    public Properties properties() throws IOException{
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException{
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(properties());

        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setStartupDelay(20);
        return schedulerFactoryBean;
    }

    /**
     * @Description quartz初始化监听器
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/8 15:10
     */
    @Bean
    public QuartzInitializerListener executorListener(){
        return new QuartzInitializerListener();
    }

    /**
    * @Description 通过schedulerFactoryBean获取Scheduler的实例
    * @Param null
    * @Return {@link null}
    * @Author teronb
    * @Date 2023/2/8 15:14
    */
    @Bean
    public Scheduler scheduler() throws IOException{
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 使用阿里的druid作为数据库连接池
     */
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
       /* schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setStartupDelay(20);*/
    }
}
