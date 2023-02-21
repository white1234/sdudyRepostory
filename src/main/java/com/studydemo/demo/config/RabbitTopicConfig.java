package com.studydemo.demo.config;

import com.studydemo.demo.em.RabbitConstantEum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.studydemo.demo.config.RabbitConstant.*;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/16 18:06
 */
@Configuration
public class RabbitTopicConfig {


    @Bean
    public Queue firstQueue() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",5000);
        args.put("x-dead-letter-exchange",EXCHANGE_DIRECT_DEAD_LETTER);
        args.put("x-dead-letter-routing-key",RK_DEAD_LETTER);
        return new Queue(QUEUE_TOPIC_MAN,true,false,false,args);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(QUEUE_TOPIC_WOMAN);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_TOPIC_TEST,true,false);
    }
    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(RK_TOPIC_MAN);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with(RK_TOPIC_WOMAN);
    }

}
