package com.ozeryavuzaslan.paymentservice.exception;

public class RefundAmountExceedsException extends RuntimeException{
    public RefundAmountExceedsException(String message){
        super(message);
    }
}
