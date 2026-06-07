package com.example.orderservice.service;

import com.example.orderservice.dto.request.CreateOrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto request);

    OrderResponseDto getOrder(Long id);

    List<OrderResponseDto> getOrders();
}
