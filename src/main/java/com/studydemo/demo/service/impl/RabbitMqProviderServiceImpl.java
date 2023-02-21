package com.studydemo.demo.service.impl;

import com.studydemo.demo.service.RabbitMqProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/17 10:38
 */
@Slf4j
@Service
public class RabbitMqProviderServiceImpl implements RabbitMqProviderService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback  {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("自定义式---->交换机接收到id为:{}的消息确认成功!", id);
        } else {
            log.info("自定义式---->id为:{}的消息未成功投递到交换机,原因是:{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("自定义式---->消息:{}被交换机:{}退回,退回码是:{},退回原因是:{},路由是:{}", returnedMessage.getMessage(), returnedMessage.getExchange(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getRoutingKey());
    }

    @Override
    public void sendTopicMessage1() {
        //true:交换机无法将消息进行路由时，会将该消息返回给生产者；false:如果发现消息无法进行路由，则直接丢弃
        // rabbitTemplate.setMandatory(true);
        //rabbitTemplate.setConfirmCallback(this);
        //rabbitTemplate.setReturnsCallback(this);

        CorrelationData correlationData = new CorrelationData("1");
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man",manMap, correlationData);
    }
}
