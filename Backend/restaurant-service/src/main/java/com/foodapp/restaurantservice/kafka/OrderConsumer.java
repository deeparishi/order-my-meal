package com.foodapp.restaurantservice.kafka;

import com.foodapp.common.dto.request.RestaurantRequest;
import com.foodapp.restaurantservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    @Autowired
    OrderService orderService;

    @KafkaListener(topics = {"${restaurant.consumer.topic.initiate}"} )
    public void orderConsumer(@Payload RestaurantRequest orderRequest,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        orderService.newOrder(orderRequest);
        log.info("Received the order from the topic {} where the order Id is {}",
                topic,
                orderRequest.getOrderId()
        );
    }
}
