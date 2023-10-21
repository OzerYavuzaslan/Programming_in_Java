package com.ozeryavuzaslan.orderservice.exception;

public class OrderNotCanceledException extends RuntimeException {
    public OrderNotCanceledException(String message) {
        super(message);
    }
}
