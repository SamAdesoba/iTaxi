package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserException extends ITaxiException {
    public InvalidUserException(String message, HttpStatus status) {
        super(message, status);
    }
}
