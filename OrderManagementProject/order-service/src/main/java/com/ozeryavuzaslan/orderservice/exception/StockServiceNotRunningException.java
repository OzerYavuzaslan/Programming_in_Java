package com.ozeryavuzaslan.orderservice.exception;

public class StockServiceNotRunningException extends RuntimeException{
    public StockServiceNotRunningException(String message){
        super(message);
    }
}