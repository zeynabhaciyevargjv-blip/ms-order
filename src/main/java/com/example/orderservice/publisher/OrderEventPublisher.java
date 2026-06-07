package com.example.orderservice.publisher;

import com.example.orderservice.config.RabbitMQConfig;
import com.example.orderservice.dto.event.OrderAssignedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderAssignedEvent(Long orderId, Long courierId) {
        OrderAssignedEvent event = new OrderAssignedEvent(orderId, courierId);
        System.out.println("RabbitMQ-yə ORDER_ASSIGNED eventi göndərilir... Sifariş ID: " + orderId);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.COURIER_ASSIGNED_ROUTING_KEY,
                event
        );
    }
}