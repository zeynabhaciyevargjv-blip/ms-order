package com.example.orderservice.dto.request;

import lombok.Data;

@Data
public class CreateOrderRequestDto {
    String pickupAddress;
    String deliveryAddress;
}
