package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class UserExistException extends ITaxiException{
    public UserExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
