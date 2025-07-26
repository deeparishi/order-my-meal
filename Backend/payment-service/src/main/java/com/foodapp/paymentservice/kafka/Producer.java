package com.foodapp.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Producer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void queue(String topic, Object message) throws ExecutionException, InterruptedException {
        CompletableFuture<SendResult<String, Object>> response = kafkaTemplate.send(topic, message);
        System.out.println("producer log: " + response.get().toString());
    }
}
