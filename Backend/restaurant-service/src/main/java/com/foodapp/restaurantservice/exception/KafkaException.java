package com.foodapp.restaurantservice.exception;

public class KafkaException extends RuntimeException{

    public KafkaException(String msg) {
        super(msg);
    }
}
