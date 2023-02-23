package com.studydemo.demo.service;

import com.alibaba.fastjson.JSON;
import com.studydemo.demo.model.entity.Order;
import com.studydemo.demo.model.entity.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/21 19:38
 */
@Component
@Slf4j
public class MqProducerService {
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    // 建议正常规模项目统一用一个TOPIC
    private static final String topic = "RLT_TEST_TOPIC";
    private static final String orderTopic = "power_node_list";

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    /**
     * @Description 普通发送（这里的参数对象User可以随意定义，可以发送个对象，也可以是字符串等）
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/21 19:43
     */
    public void send(SysUserInfo user){
        rocketMQTemplate.send(topic+":tag1", MessageBuilder.withPayload(user).build());
    }

    /**
     * @Description 发送同步消息（阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
     *              （msgBody也可以是对象，sendResult为返回的发送结果）
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/21 19:46
     */
    public SendResult sendSyncMess(SysUserInfo user){
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(user).build());
        log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
        return sendResult;
    }

    /**
     * @Description  发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     *              （适合对响应时间敏感的业务场景）
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/21 19:50
     */
    public void sendAsyncMess(SysUserInfo user){
        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(user).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("消息发送失败");
            }
        });
    }

    /**
     * @Description
     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/21 20:09
     */
    public void sendDelayMsg(String msgBody, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
    }

    /**
     * 发送单向消息（只负责发送消息，不等待应答，不关心发送结果，如日志）
     */
    public void sendOneWayMsg(String msgBody) {
        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送带tag的消息，直接在topic后面加上":tag"
     */
    public SendResult sendTagMsg(String msgBody) {
        return rocketMQTemplate.syncSend(topic + ":tag2", MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送顺序消息
     */
    public void sendOrderListMsg(List<Order> orders)
    {
        orders.forEach(order -> {
            rocketMQTemplate.syncSendOrderly(orderTopic, order, String.valueOf(order.getSeq()));
        });
    }

    /**
     * 发送事务消息
     */
    public void sendTranMsg(String msg)throws Exception
    {
        // 构建消息体
        Message<String> message = MessageBuilder.withPayload("这是一个事务消息").build();
        // 发送事务消息（同步的） 最后一个参数才是消息主题
        TransactionSendResult transaction = rocketMQTemplate.sendMessageInTransaction("Pro_Group","powernode", message, "消息的参数");
        // 拿到本地事务状态
        System.out.println(transaction.getLocalTransactionState());
        // 挂起jvm，因为事务的回查需要一些时间
        System.in.read();

    }

}
