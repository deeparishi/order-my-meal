package com.foodapp.paymentservice.kafka;

import com.foodapp.common.dto.request.PaymentRequest;
import com.foodapp.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "${consumer.order.topic.payment-initiate}")
    public void paymentInitiate(@Payload PaymentRequest paymentRequest, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received payment request from {} for amount {} on provider {} at topic {}",
                    paymentRequest.getEmailId(),
                    paymentRequest.getAmount(),
                    paymentRequest.getProviderId(),
                    topic);
            paymentService.initiatePayment(paymentRequest);
        } catch (Exception e) {
            log.error("Error processing payment request: {}", e.getMessage(), e);
        }
    }
}
