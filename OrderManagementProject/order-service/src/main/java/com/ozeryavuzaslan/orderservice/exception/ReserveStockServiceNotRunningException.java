package com.ozeryavuzaslan.orderservice.exception;

public class ReserveStockServiceNotRunningException extends RuntimeException{
    public ReserveStockServiceNotRunningException(String message){
        super(message);
    }
}