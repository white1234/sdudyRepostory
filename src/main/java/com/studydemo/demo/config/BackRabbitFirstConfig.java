package com.studydemo.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author by
 */
//@Configuration
public class BackRabbitFirstConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BackRabbitFirstConfig.class);

    @PostConstruct
    private void init() {
        //true:交换机无法将消息进行路由时，会将该消息返回给生产者；false:如果发现消息无法进行路由，则直接丢弃
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            logger.info("全局式---->交换机接收到id为:{}的消息确认成功!", id);
        } else {
            logger.info("全局式---->id为:{}的消息未成功投递到交换机,原因是:{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        logger.error("全局式---->消息:{}被交换机:{}退回,退回码是:{},退回原因是:{},路由是:{}", returnedMessage.getMessage(), returnedMessage.getExchange(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getRoutingKey());
    }
}
