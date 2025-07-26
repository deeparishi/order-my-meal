package com.foodapp.restaurantservice.exception;

public class NotFound extends RuntimeException {

    public NotFound(String message) {
        super(message);
    }
}
