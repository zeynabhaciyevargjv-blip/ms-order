package com.example.orderservice.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path){
}
