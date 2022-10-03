package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidDriverException extends ITaxiException {
    public InvalidDriverException(String message, HttpStatus status) {
        super(message, status);
    }
}
