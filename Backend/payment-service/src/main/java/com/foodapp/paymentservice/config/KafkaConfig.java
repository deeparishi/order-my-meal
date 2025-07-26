package com.foodapp.paymentservice.config;

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

    @Value("${payment.producer.topic.processing.name}")
    private String paymentProcessing;

    @Value("${payment.producer.topic.processing.partition}")
    private int paymentProcessingPartition;

    @Value("${payment.producer.topic.processing.replication-factor}")
    private int paymentProcessingFactor;

    @Value("${payment.producer.topic.success.name}")
    private String paymentSuccess;

    @Value("${payment.producer.topic.success.partition}")
    private int paymentSuccessPartition;

    @Value("${payment.producer.topic.success.replication-factor}")
    private int paymentSuccessFactor;

    @Value("${payment.producer.topic.failed.name}")
    private String paymentFailed;

    @Value("${payment.producer.topic.failed.partition}")
    private int paymentFailedPartition;


    @Value("${payment.producer.topic.failed.replication-factor}")
    private int paymentFailedFactor;

    @Bean
    public NewTopic paymentProcessingTopic() {
        return new NewTopic(paymentProcessing, paymentProcessingPartition, (short) paymentProcessingFactor);
    }

    @Bean
    public NewTopic paymentSuccessTopic() {
        return new NewTopic(paymentSuccess, 3, (short) paymentSuccessFactor);
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return new NewTopic(paymentFailed, 3, (short) paymentFailedFactor);
    }

    @Bean
    public CommonErrorHandler errorHandler() {

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, ex) -> log.error("Error processing record: {}, Exception: {}", consumerRecord.value(), ex.getMessage()),
                new FixedBackOff(1000L, 3)
        );

        errorHandler.addNotRetryableExceptions(
                org.springframework.kafka.support.serializer.DeserializationException.class
        );

        return errorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

}
