package com.foodapp.orderservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void queue(String topic, Object data) throws ExecutionException, InterruptedException {
        CompletableFuture<SendResult<String, Object>> response = kafkaTemplate.send(topic, data);
        System.out.println("producer log: " + response.get());
    }
}
