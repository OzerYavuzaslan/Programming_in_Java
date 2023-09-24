package com.ozeryavuzaslan.paymentservice.exception;

public class RefundNotFoundException extends RuntimeException{
    public RefundNotFoundException(String message){
        super(message);
    }
}
