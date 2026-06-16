package com.example.orderservice.exception;

public class NotAvailableException extends RuntimeException {
    public NotAvailableException(String message) {
        super(message);
    }
}
