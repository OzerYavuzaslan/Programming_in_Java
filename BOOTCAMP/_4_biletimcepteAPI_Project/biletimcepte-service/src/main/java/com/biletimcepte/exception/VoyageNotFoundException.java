package com.biletimcepte.exception;

public class VoyageNotFoundException extends RuntimeException{
    public VoyageNotFoundException(String message) {
        super(message);
    }
}