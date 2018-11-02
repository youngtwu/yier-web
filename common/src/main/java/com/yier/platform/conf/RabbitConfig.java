package com.yier.platform.conf;

public class RabbitConfig {
    private String registerDelayQueue = "com.yier.platform.service.medical.delay.queue";
    private String orderDelayExchange = "com.yier.platform.service.medical.delay.exchange";
    private String orderQueueName = "com.yier.platform.service.medical.queue";
    private String orderExchangeName = "com.yier.platform.service.medical.exchange";

    public String getRegisterDelayQueue() {
        return registerDelayQueue;
    }

    public void setRegisterDelayQueue(String registerDelayQueue) {
        this.registerDelayQueue = registerDelayQueue;
    }

    public String getOrderDelayExchange() {
        return orderDelayExchange;
    }

    public void setOrderDelayExchange(String orderDelayExchange) {
        this.orderDelayExchange = orderDelayExchange;
    }

    public String getOrderQueueName() {
        return orderQueueName;
    }

    public void setOrderQueueName(String orderQueueName) {
        this.orderQueueName = orderQueueName;
    }

    public String getOrderExchangeName() {
        return orderExchangeName;
    }

    public void setOrderExchangeName(String orderExchangeName) {
        this.orderExchangeName = orderExchangeName;
    }
}
