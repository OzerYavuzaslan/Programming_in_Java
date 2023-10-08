package com.ozeryavuzaslan.orderservice.exception;

public class RevenueServiceNotRunningException extends RuntimeException{
    public RevenueServiceNotRunningException(String message){
        super(message);
    }
}