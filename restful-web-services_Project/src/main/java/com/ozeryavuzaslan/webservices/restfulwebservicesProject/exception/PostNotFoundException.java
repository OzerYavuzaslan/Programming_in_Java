package com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message){
        super(message);
    }
}
