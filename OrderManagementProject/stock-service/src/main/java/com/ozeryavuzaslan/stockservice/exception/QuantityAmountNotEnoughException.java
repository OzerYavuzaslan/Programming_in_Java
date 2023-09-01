package com.ozeryavuzaslan.stockservice.exception;

public class QuantityAmountNotEnoughException extends RuntimeException{
    public QuantityAmountNotEnoughException(String message){
        super(message);
    }
}
