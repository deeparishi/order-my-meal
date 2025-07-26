package com.foodapp.paymentservice.exception;

public class KafkaException extends RuntimeException{

    public KafkaException(String msg) {
        super(msg);
    }
}
