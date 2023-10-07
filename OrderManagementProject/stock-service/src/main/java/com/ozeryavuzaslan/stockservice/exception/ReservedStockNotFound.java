package com.ozeryavuzaslan.stockservice.exception;

public class ReservedStockNotFound extends RuntimeException{
    public ReservedStockNotFound(String message){
        super(message);
    }
}
