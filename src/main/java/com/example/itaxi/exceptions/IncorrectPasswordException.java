package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends ITaxiException {
    public IncorrectPasswordException(String message, HttpStatus status) {
        super(message, status);
    }
}
