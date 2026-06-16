package com.example.orderservice.client;

import com.example.orderservice.dto.response.CourierResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "courier-service")
public interface CourierFeignClient {

    @GetMapping("/api/couriers/available")
    CourierResponseDto getAvailableCourier();
}
