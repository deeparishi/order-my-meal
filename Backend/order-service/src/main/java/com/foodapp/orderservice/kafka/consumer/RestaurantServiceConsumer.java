package com.foodapp.orderservice.kafka.consumer;

import com.foodapp.orderservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RestaurantServiceConsumer {

    @Autowired
    RestaurantService restaurantService;

    @KafkaListener(topics = ("${order.consumer.topic.restaurant.event}"))
    public void restaurantEvent(@Payload Map<String, String> response,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("response of restaurant event! {} from topic {}", response, topic);
        restaurantService.responseOfRestaurantService(response);
    }
}
