package com.ozeryavuzaslan.revenueservice.exception;

public class TaxRateNotFoundException extends RuntimeException{
    public TaxRateNotFoundException(String message){
        super(message);
    }
}
