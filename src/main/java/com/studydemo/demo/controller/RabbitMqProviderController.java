package com.studydemo.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.studydemo.demo.model.LogQueue;
import com.studydemo.demo.model.entity.SysLog;
import com.studydemo.demo.service.RabbitMqProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/16 18:13
 */
@Slf4j
@RestController
@RequestMapping("/rabbitMq")
public class RabbitMqProviderController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    LogQueue logQueue;

    @Autowired
    RabbitMqProviderService mqProviderService;

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        mqProviderService.sendTopicMessage1();
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        CorrelationData correlationData = new CorrelationData();
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);

        correlationData.setId(messageId);

        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setMessageId(messageId);
            messageProperties.setContentEncoding("UTF-8");
            messageProperties.setExpiration("60000");
            return message;
        };
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap,messagePostProcessor, correlationData);
        correlationData.getFuture().addCallback(result->{
            if (result.isAck()){
                log.debug("消息发送成功, ID:{}", correlationData.getId());
                logQueue.add(new SysLog(DateUtil.formatDateTime(new Date()),"消息发送成功, ID:"+correlationData.getId(),"topic响应","rabbitmq"));
            }

            },
                ex->log.error("消息发送异常, ID:{}, 原因{}",correlationData.getId(),ex.getMessage())
        );
        return "ok";
    }
}
