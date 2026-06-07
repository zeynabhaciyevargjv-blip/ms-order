package com.example.orderservice.dto;

import lombok.Data;

@Data
public class CourierResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String status;
}