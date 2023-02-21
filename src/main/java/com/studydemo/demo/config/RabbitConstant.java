package com.studydemo.demo.config;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/20 10:17
 */
public class RabbitConstant {
    /**
     * EXCHANGE 交换机
     */
    public static final String EXCHANGE_DIRECT_TEST="TestDirectExchange";
    public static final String EXCHANGE_TOPIC_TEST="topicExchange";
    public static final String EXCHANGE_FANOUT_TEST="TestFanoutExchange";
    public static final String EXCHANGE_DIRECT_DEAD_LETTER="deadLetterE";

    /**
     * QUEUE 队列名
     */
    public static final String QUEUE_DIRECT_TEST="TestDirectQueue";
    public static final String QUEUE_TOPIC_MAN="topic.man";
    public static final String QUEUE_TOPIC_WOMAN="topic.woman";
    public static final String QUEUE_FANOUT_A="fanout.A";
    public static final String QUEUE_DEAD_LETTER="letter.dead";

    /**
     * ROUTING_KEY 路由KEY
     */
    public static final String RK_DIRECT_TEST="TestDirectRouting";
    public static final String RK_TOPIC_MAN="topic.man";
    public static final String RK_TOPIC_WOMAN="topic.#";
    public static final String RK_DEAD_LETTER="letter.dead";


}
