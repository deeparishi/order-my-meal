package com.foodapp.orderservice.exception;

public class KafkaException extends RuntimeException{

    public KafkaException(String msg) {
        super(msg);
    }
}
