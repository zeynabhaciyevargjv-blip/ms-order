package com.example.orderservice.service.impl;


import com.example.orderservice.Enum.OrderStatus;
import com.example.orderservice.dto.request.CreateOrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.IdNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {
        Order order = Order.builder()
                .pickupAddress(request.getPickupAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .deliveryFee(5.0)
                .status(OrderStatus.CREATED)
                .build();

        Order saveOrder = orderRepository.save(order);

        return modelMapper.map(saveOrder, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto getOrder(Long id) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new IdNotFoundException("Order not found"));

        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAllByIsDeletedFalse()
                .stream().map(allOrder-> modelMapper.map(allOrder,OrderResponseDto.class)).toList();
    }
}
