package com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}