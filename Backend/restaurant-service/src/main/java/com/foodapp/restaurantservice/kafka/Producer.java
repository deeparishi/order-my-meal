package com.foodapp.restaurantservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class Producer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void queue(String topicName, Object data) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topicName, data);
        log.info("Data sent to kafka from topic {} and data {} ", topicName, data);
        try {
            log.info("Result from kafka {}", send.get().getRecordMetadata());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
