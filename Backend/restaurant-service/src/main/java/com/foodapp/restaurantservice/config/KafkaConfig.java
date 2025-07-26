package com.foodapp.restaurantservice.config;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class KafkaConfig {

    @Value("${restaurant.producer.topic.name}")
    private String topicName;

    @Value("${restaurant.producer.topic.partitions}")
    private int partitions;

    @Value("${restaurant.producer.topic.replication-factor}")
    private int replicationFactor;

    @Bean
    public NewTopic restaurantEventTopic() {
        return new NewTopic(topicName, partitions, (short) replicationFactor);
    }

//    @Bean
//    public CommonErrorHandler errorHandler() {
//
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
//                (consumerRecord, ex) -> log.error("Error processing record: {}, Exception: {}", consumerRecord.value(), ex.getMessage()),
//                new FixedBackOff(1000L, 3)
//        );
//
//        errorHandler.addNotRetryableExceptions(
//                org.springframework.kafka.support.serializer.DeserializationException.class
//        );
//
//        return errorHandler;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
//        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

}
