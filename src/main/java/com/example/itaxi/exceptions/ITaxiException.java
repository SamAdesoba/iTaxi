package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class ITaxiException extends Exception{
    private HttpStatus status;
    
    public ITaxiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

}

