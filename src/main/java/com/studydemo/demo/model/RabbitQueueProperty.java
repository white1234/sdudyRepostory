package com.studydemo.demo.model;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @Description
 * @Author teronb
 * @Date 2023/2/17 9:06
 */
//标明为配置类将其注入spring容器中
@Configuration
//从application.yml文件中前缀为spring.rabbitmq中获取配置信息
@ConfigurationProperties(prefix = "spring.rabbitmq")
//lombok注解，属性可以自动get，set
@Data
public class RabbitQueueProperty {


    //fanout交换机名称
    private String fanoutExchange;

    //direct交换机名称
    private String directExchange;

    //fanout队列名称
    private String fanoutMessageQueue;

    //direct队列名称
    private String directMessageQueue;

    //direct的路由key
    private String directRoutingKey;

    //死信交换机名称
    private String deadLetterDirectExchange;


    //延时队列名称
    private String delayLetterQueue;

    //延时交换机的路由key
    private String delayLetterRoutingKey;

    //死信队列名称
    private String deadLetterQueue;

    //死信交换机的路由key
    private String deadLetterRoutingKey;

    //异常消息队列
    private String exceptionMsgQueue;
    //异常消息路由key
    private String exceptionMsgRoutingKey;

    //消息重试间隔时间
    private Integer retryTime;
}


