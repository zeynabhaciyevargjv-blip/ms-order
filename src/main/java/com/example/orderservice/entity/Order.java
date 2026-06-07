package com.example.orderservice.entity;

import com.example.orderservice.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String pickupAddress;
    String deliveryAddress;
    Long courierId;
    Double deliveryFee;
    Boolean isDeleted;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
