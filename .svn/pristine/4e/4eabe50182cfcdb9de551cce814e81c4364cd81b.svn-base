package com.yier.platform.service.amqp;

import com.yier.platform.conf.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);


        return rabbitTemplate;
    }

    //private static final String REGISTER_DELAY_QUEUE = "Test.com.yier.platform.service.medical.delay.queue"; //applicationConfig.getRabbitConfig().getRegisterDelayQueue()
    //public static final String ORDER_DELAY_EXCHANGE = "Test.com.yier.platform.service.medical.delay.exchange";//applicationConfig.getRabbitConfig().getOrderDelayExchange()
    public static final String ORDER_DELAY_ROUTING_KEY = "medical_routing";

    public static final String ORDER_QUEUE_NAME = "dev.web.com.yier.platform.service.medical.queue";//applicationConfig.getRabbitConfig().getOrderQueueName()
    //public static final String ORDER_EXCHANGE_NAME = "Test.com.yier.platform.service.medical.exchange";//applicationConfig.getRabbitConfig().getOrderExchangeName()
    public static final String ROUTING_KEY = "medical_routing_all";

    @Bean
    public Queue delayProcessQueue() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x-dead-letter-exchange", applicationConfig.getRabbitConfig().getOrderExchangeName());
        params.put("x-dead-letter-routing-key", ROUTING_KEY);
        return new Queue(applicationConfig.getRabbitConfig().getRegisterDelayQueue(), true, false, false, params);
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(applicationConfig.getRabbitConfig().getOrderDelayExchange());
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayProcessQueue()).to(delayExchange()).with(ORDER_DELAY_ROUTING_KEY);
    }


    @Bean
    public Queue registerBookQueue() {
        return new Queue(ORDER_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange registerBookTopicExchange() {
        return new TopicExchange(applicationConfig.getRabbitConfig().getOrderExchangeName());
    }

    @Bean
    public Binding registerBookBinding() {
        return BindingBuilder.bind(registerBookQueue()).to(registerBookTopicExchange()).with(ROUTING_KEY);
    }
}
