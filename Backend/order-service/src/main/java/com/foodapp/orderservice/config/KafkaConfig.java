package com.foodapp.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {


    @Value("${order.producer.topic.payment.name}")
    private String paymentInitiateTopic;

    @Value("${order.producer.topic.payment.partition}")
    private int paymentInitiatePartition;

    @Value("${order.producer.topic.payment.replication-factor}")
    private int paymentInitiateFactor;


    @Bean
    public NewTopic paymentInitiateTopic() {
        return new NewTopic(paymentInitiateTopic, paymentInitiatePartition, (short) paymentInitiateFactor);
    }

    @Value("${order.producer.topic.restaurant.name}")
    private String restaurantInitiateTopic;

    @Value("${order.producer.topic.restaurant.partition}")
    private int restaurantInitiatePartition;

    @Value("${order.producer.topic.restaurant.replication-factor}")
    private int restaurantInitiateFactor;


    @Bean
    public NewTopic restaurantInitiateTopic() {
        return new NewTopic(restaurantInitiateTopic, restaurantInitiatePartition, (short) restaurantInitiateFactor);
    }

    @Value("${order.producer.topic.delivery.name}")
    private String deliveryInitiateTopic;

    @Value("${order.producer.topic.delivery.partition}")
    private int deliveryInitiatePartition;

    @Value("${order.producer.topic.delivery.replication-factor}")
    private int deliveryInitiateFactor;


    @Bean
    public NewTopic deliveryInitiateTopic() {
        return new NewTopic(deliveryInitiateTopic, deliveryInitiatePartition, (short) deliveryInitiateFactor);
    }


}