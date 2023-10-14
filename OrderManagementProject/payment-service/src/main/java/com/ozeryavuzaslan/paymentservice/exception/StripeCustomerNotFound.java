package com.ozeryavuzaslan.paymentservice.exception;

public class StripeCustomerNotFound extends RuntimeException{
    public StripeCustomerNotFound(String message){
        super(message);
    }
}
