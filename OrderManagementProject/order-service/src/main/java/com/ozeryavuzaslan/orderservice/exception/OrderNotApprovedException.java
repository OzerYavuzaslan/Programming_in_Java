package com.ozeryavuzaslan.orderservice.exception;

public class OrderNotApprovedException extends RuntimeException {
    public OrderNotApprovedException(String message){
        super(message);
    }
}
