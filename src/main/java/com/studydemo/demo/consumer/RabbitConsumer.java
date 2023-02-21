package com.studydemo.demo.consumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/16 16:08
 */
@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(queues = "TestDirectQueue")
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

    //@RabbitListener(queues = "topic.man")
    public void topicManProcess(Map testMessage){
        System.out.println("TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }

    //@RabbitListener(queues = "topic.woman")
    public void topicWomanProcess(Map orderMsg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            String messageId = (String)orderMsg.get("messageId");
            String messageData = (String)orderMsg.get("messageData");
            String createTime = (String)orderMsg.get("createTime");

            log.info("women接收到消息：messageId=" + messageId + ",messageData=" + messageData + ",createTime=" + createTime);
            // 派单处理
            //dispatcherService.dispatch(orderId);

          //  System.out.println(1 / 0); // 出现异常
            // 手动确认
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 如果出现异常的情况下 根据实际情况重发
            // 重发一次后，丢失
            // 参数1：消息的tag
            // 参数2：多条处理
            // 参数3：重发
            // false 不会重发，会把消息打入到死信队列
            // true 重发，建议不使用try/catch 否则会死循环

            // 手动拒绝消息
            log.error(e.getMessage());
            channel.basicNack(tag, false, false);
        }
    }
}
