package com.example.orderservice.dto.response;

import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long courierId;
    private String pickupAddress;
    private String deliveryAddress;
    private String status;
    private Double price;
}