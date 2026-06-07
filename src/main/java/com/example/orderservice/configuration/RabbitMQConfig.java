package com.example.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order-exchange";

    public static final String COURIER_ASSIGNED_QUEUE = "courier-order-assigned-queue";
    public static final String COURIER_ASSIGNED_ROUTING_KEY = "order.assigned.courier";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue courierAssignedQueue() {
        return new Queue(COURIER_ASSIGNED_QUEUE, true);
    }

    @Bean
    public Binding bindingCourierAssigned(Queue courierAssignedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(courierAssignedQueue).to(orderExchange).with(COURIER_ASSIGNED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}