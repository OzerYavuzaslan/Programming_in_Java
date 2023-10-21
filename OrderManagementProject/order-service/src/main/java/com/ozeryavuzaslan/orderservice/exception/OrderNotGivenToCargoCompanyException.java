package com.ozeryavuzaslan.orderservice.exception;

public class OrderNotGivenToCargoCompanyException extends RuntimeException {
    public OrderNotGivenToCargoCompanyException(String message) {
        super(message);
    }
}
