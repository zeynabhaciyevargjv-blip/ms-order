package com.example.orderservice.service.impl;


import com.example.orderservice.Enum.OrderStatus;
import com.example.orderservice.client.CourierFeignClient;
import com.example.orderservice.dto.request.CreateOrderRequestDto;
import com.example.orderservice.dto.response.CourierResponseDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.IdNotFoundException;
import com.example.orderservice.exception.NotAvailableException;
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
    private final CourierFeignClient courierFeignClient;
    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {

        CourierResponseDto courier =
                courierFeignClient.getAvailableCourier();

        if (courier == null) {
            throw new NotAvailableException("No courier available");
        }
        Order order = Order.builder()
                .pickupAddress(request.getPickupAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .courierId(courier.id())
                .deliveryFee(5.0)
                .status(OrderStatus.ASSIGNED)
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
