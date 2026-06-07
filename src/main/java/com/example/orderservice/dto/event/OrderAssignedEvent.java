package com.example.orderservice.dto.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssignedEvent {
    private Long orderId;
    private Long courierId;
}