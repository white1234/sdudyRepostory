package com.studydemo.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author by
 */
public interface RabbitMqProviderService{
    public void sendTopicMessage1();
}
