package com.example.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidActionException extends ITaxiException {
    public InvalidActionException(String message, HttpStatus status) {
        super(message, status);
    }
}
