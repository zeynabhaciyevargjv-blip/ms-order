package com.example.orderservice.dto.response;

import com.example.orderservice.Enum.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponseDto {
    Long id;

    String pickupAddress;

    String deliveryAddress;

    Long courierId;

    BigDecimal deliveryFee;

    OrderStatus status;
}
