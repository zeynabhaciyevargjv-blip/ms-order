package com.example.orderservice.client;

import com.example.orderservice.dto.CourierResponseDto;import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "courier-service", url = "http://localhost:8081/api/couriers")
public interface CourierClient {

    @GetMapping("/available")
    CourierResponseDto getAvailableCourier();
}