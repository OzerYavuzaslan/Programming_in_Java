package com.ozeryavuzaslan.orderservice.exception;

public class OrderNotDeliveredException extends RuntimeException {
    public OrderNotDeliveredException(String message) {
        super(message);
    }
}
