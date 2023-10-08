package com.ozeryavuzaslan.orderservice.exception;

public class PaymentServiceNotRunningException extends RuntimeException{
    public PaymentServiceNotRunningException(String message){
        super(message);
    }
}