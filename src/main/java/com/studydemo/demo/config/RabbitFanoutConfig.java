package com.studydemo.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/17 8:55
 */
@Configuration
public class RabbitFanoutConfig {

    @Bean
    public Queue queueA(){
        return new Queue("Fanout.A");
    }
    @Bean
    public Queue queueB(){
        return new Queue("Fanout.B");
    }
    @Bean
    public Queue queueC(){
        return new Queue("Fanout.C");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        // 创建一个名称是 fanout_exchange 的交换机，允许持久化， 自动删除关闭
        return new FanoutExchange("TestFanoutExchange", true, false);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }
}
