package com.studydemo.demo.em;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/20 10:04
 */
public enum RabbitConstantEum {

    RABBIT_MQ_DIRECT("TestDirectExchange","TestDirectQueue","TestDirectRouting"),
    RABBIT_MQ_TOPIC_MAN("topicExchange","topic.man","topic.man"),
    RABBIT_MQ_TOPIC_WOMAN("topicExchange","topic.woman","topic.#")
    ;
    private String exchangeName;
    private String queueName;
    private String routingKey;

    RabbitConstantEum(String exchangeName, String queueName,String routingKey) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
