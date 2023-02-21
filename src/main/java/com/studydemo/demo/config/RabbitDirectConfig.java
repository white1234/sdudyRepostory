package com.studydemo.demo.config;

import com.studydemo.demo.em.RabbitConstantEum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.studydemo.demo.config.RabbitConstant.*;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/16 12:50
 */
@Configuration
public class RabbitDirectConfig {
    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // return new Queue("TestDirectQueue",true,true,false);

        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);    // 注意是 int 类型 (这些参数可在 rabbitmq管理页查看)
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(QUEUE_DIRECT_TEST,true,false,false,args);
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue(QUEUE_DEAD_LETTER);
    }


    @Bean
    DirectExchange TestDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange(EXCHANGE_DIRECT_TEST,true,false);
    }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(EXCHANGE_DIRECT_DEAD_LETTER);
    }

    //绑定
    //将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with(RK_DIRECT_TEST);
    }

    @Bean
    Binding bindingDeadE(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RK_DEAD_LETTER);
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }
}
