package com.biletimcepte.exception;

public class EmptyTicketListException extends RuntimeException{
    public EmptyTicketListException(String message){
        super(message);
    }
}
