package com.ozeryavuzaslan.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message){
        super(message);
    }
}
