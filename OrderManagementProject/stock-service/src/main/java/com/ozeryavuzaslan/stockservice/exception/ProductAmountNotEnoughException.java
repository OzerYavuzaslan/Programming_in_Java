package com.ozeryavuzaslan.stockservice.exception;

public class ProductAmountNotEnoughException extends RuntimeException{
    public ProductAmountNotEnoughException(String message){
        super(message);
    }
}