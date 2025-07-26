package com.foodapp.restaurantservice.exception;

public class FeignClientException extends RuntimeException{

    public FeignClientException(String msg) {
        super(msg);
    }
}
