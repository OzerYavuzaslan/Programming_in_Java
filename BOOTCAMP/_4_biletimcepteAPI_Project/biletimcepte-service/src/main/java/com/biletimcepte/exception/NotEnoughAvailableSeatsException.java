package com.biletimcepte.exception;

public class NotEnoughAvailableSeatsException extends RuntimeException {
    public NotEnoughAvailableSeatsException(String message){
        super(message);
    }
}
