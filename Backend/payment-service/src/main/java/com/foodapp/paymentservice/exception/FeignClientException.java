package com.foodapp.paymentservice.exception;

public class FeignClientException extends RuntimeException{

    public FeignClientException(String msg) {
        super(msg);
    }
}
