package com.studydemo.demo.controller;

import cn.hutool.core.lang.UUID;
import com.studydemo.demo.model.entity.Order;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.MqProducerService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/21 20:20
 */
@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private MqProducerService mqProducerService;

    @GetMapping("/send")
    public void send() {
        SysUserInfo user =new SysUserInfo();
        user.setId(UUID.fastUUID().getLeastSignificantBits());
        user.setUsername("hello rocket");
        mqProducerService.send(user);
    }

    @GetMapping("/sendTag")
    public BaseResponse<SendResult> sendTag() {
        SendResult sendResult = mqProducerService.sendTagMsg("带有tag的字符消息");
        return RespGenerator.success(sendResult);
    }

    @GetMapping("/sendOrderMessage")
    public void sendOrderMessage(){
        List<Order> orders = Arrays.asList(
                new Order(UUID.randomUUID().toString().substring(0, 5), "张三的下订单", 234d, new Date(), null, 1),
                new Order(UUID.randomUUID().toString().substring(0, 5), "张三的发短信", 567d, new Date(), null, 1),
                new Order(UUID.randomUUID().toString().substring(0, 5), "张三的物流", 456.98d, new Date(), null, 1),
                new Order(UUID.randomUUID().toString().substring(0, 5), "张三的签收", 986.23, new Date(), null, 1),

                new Order(UUID.randomUUID().toString().substring(0, 5), "李四的下订单", 7895.10, new Date(), null, 2),
                new Order(UUID.randomUUID().toString().substring(0, 5), "李四的发短信", 752d, new Date(), null, 2),
                new Order(UUID.randomUUID().toString().substring(0, 5), "李四的物流", 7845.1, new Date(), null, 2),
                new Order(UUID.randomUUID().toString().substring(0, 5), "李四的签收", 8965.23, new Date(), null, 2)
        );
        Map<String, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getOrderId, Function.identity(), ((order, order2) -> order2)));
        Iterator<Map.Entry<String, Order>> iterator = orderMap.entrySet().iterator();
        for (Map.Entry entry:orderMap.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        mqProducerService.sendOrderListMsg(orders);
    }

}
