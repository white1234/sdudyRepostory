package com.studydemo.demo.consumer;

import com.alibaba.fastjson.JSON;
import com.studydemo.demo.model.entity.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/21 20:17
 */
@Slf4j
@Component
public class MQConsumerService {

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", selectorExpression = "tag1", consumerGroup = "Con_Group_One")
    public class ConsumerSend implements RocketMQListener<SysUserInfo> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(SysUserInfo user) {
            log.info("监听到消息：user={}", JSON.toJSONString(user));
        }
    }

    // 注意：这个ConsumerSend2和上面ConsumerSend在没有添加tag做区分时，不能共存，
    // 不然生产者发送一条消息，这两个都会去消费，如果类型不同会有一个报错，所以实际运用中最好加上tag，写这只是让你看知道就行
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", consumerGroup = "Con_Group_Two")
    public class ConsumerSend2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String str) {
            log.info("监听到消息：str={}", str);
        }
    }

    // MessageExt：是一个消息接收通配符，不管发送的是String还是对象，都可接收，当然也可以像上面明确指定类型（我建议还是指定类型较方便）
    @Service
    @RocketMQMessageListener(topic = "RLT_TEST_TOPIC", selectorExpression = "tag2", consumerGroup = "Con_Group_Three")
    public class Consumer implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt messageExt) {
            byte[] body = messageExt.getBody();
            String msg = new String(body);
            log.info("监听到消息：msg={}", msg);
        }
    }

    @Service
    @RocketMQMessageListener(topic = "power_node_list",consumerGroup = "Con_Group_Three1",
            consumeMode = ConsumeMode.ORDERLY,
            selectorExpression = "tag0",
            messageModel= MessageModel.CLUSTERING )
    public class OrderlyConsumer1 implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

        @Override
        public void onMessage(String message) {
            log.info("Consumer1监听到消息：msg={}", message);
        }

        @Override
        public void prepareStart(DefaultMQPushConsumer consumer) {
            consumer.setInstanceName("power_nod e_list-tag0");
        }
    }

    @Service
    @RocketMQMessageListener(topic = "power_node_list",consumerGroup = "Con_Group_Three1",
            consumeMode = ConsumeMode.ORDERLY,
            selectorExpression = "*",
            messageModel= MessageModel.CLUSTERING )
    public class OrderlyConsumer2 implements RocketMQListener<String>{

        @Override
        public void onMessage(String message) {
            log.info("Consumer2监听到消息：msg={}", message);
        }
    }

}
