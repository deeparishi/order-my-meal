package com.foodapp.orderservice.exception;

public class NotFound extends RuntimeException {

    public NotFound(String message) {
        super(message);
    }
}
