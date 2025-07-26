package com.foodapp.orderservice.kafka.consumer;

import com.foodapp.orderservice.service.PaymentService;
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
public class PaymentServiceConsumer {

    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "${order.consumer.topic.payment.processing}")
    public void processingQueue(
            @Payload Map<String, String> payload,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("payment processing queue received a data of {} from this topic {}", payload, topic);
        paymentService.processingEvent(payload);
    }

    @KafkaListener(topics = "${order.consumer.topic.payment.success}")
    public void  successQueue(
            @Payload Map<String, String> payload,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("payment success queue received a data of {} from this topic {}", payload, topic);
        paymentService.successEvent(payload);
    }

    @KafkaListener(topics = "${order.consumer.topic.payment.failed}")
    public void failedQueue(
            @Payload Map<String, String> payload,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("payment failed queue received a data of {} from this topic {}", payload, topic);
        paymentService.failedEvent(payload);
    }
}
