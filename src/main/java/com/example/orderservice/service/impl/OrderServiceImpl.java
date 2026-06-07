package com.example.orderservice.service;

import com.example.orderservice.dto.CourierResponseDto;
import com.example.orderservice.client.CourierClient;
import com.example.orderservice.dto.request.CreateOrderRequestDto;
import com.example.orderservice.dto.response.OrderResponseDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.publisher.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CourierClient courierClient;
    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher; // Bayaq əlavə etdiyimiz publisher

    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {
        System.out.println("Yeni sifariş yaradılır. Boş kuryer sorğulanır...");

        CourierResponseDto courier;
        try {

            courier = courierClient.getAvailableCourier();
        } catch (Exception e) {
            throw new RuntimeException("Sifariş rədd edildi (REJECTED): Hazırda boş kuryer yoxdur!");
        }

        if (courier == null || courier.getId() == null) {
            throw new RuntimeException("Sifariş rədd edildi (REJECTED): Boş kuryer tapılmadı!");
        }

        System.out.println("Kuryer tapıldı! ID: " + courier.getId());

        Order order = new Order();
        order.setPickupAddress(request.getPickupAddress());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setCourierId(courier.getId());
        order.setStatus("ASSIGNED");
        order.setPrice(15.00);

        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.publishOrderAssignedEvent(savedOrder.getId(), savedOrder.getCourierId());

        OrderResponseDto response = new OrderResponseDto();
        response.setId(savedOrder.getId());
        response.setCourierId(savedOrder.getCourierId());
        response.setPickupAddress(savedOrder.getPickupAddress());
        response.setDeliveryAddress(savedOrder.getDeliveryAddress());
        response.setStatus(savedOrder.getStatus());
        response.setPrice(savedOrder.getPrice());

        return response;
    }

    @Override
    public OrderResponseDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı!"));

        OrderResponseDto response = new OrderResponseDto();
        response.setId(order.getId());
        response.setCourierId(order.getCourierId());
        response.setPickupAddress(order.getPickupAddress());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setStatus(order.getStatus());
        response.setPrice(order.getPrice());
        return response;
    }

    @Override
    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll().stream().map(order -> {
            OrderResponseDto response = new OrderResponseDto();
            response.setId(order.getId());
            response.setCourierId(order.getCourierId());
            response.setPickupAddress(order.getPickupAddress());
            response.setDeliveryAddress(order.getDeliveryAddress());
            response.setStatus(order.getStatus());
            response.setPrice(order.getPrice());
            return response;
        }).toList();
    }
}