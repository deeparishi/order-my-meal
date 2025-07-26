package com.foodapp.orderservice.service;

import com.foodapp.common.dto.request.PaymentRequest;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.common.utils.QueueKey;
import com.foodapp.orderservice.dto.request.OrderRequest;
import com.foodapp.orderservice.enums.Enum;
import com.foodapp.orderservice.exception.KafkaException;
import com.foodapp.orderservice.kafka.Producer;
import com.foodapp.orderservice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PaymentService {

    @Value("${order.producer.topic.payment.name}")
    private String paymentInitiateTopic;

    private final OrderService orderService;
    private final Producer producer;
    private final RestaurantService restaurantService;

    public PaymentService(
            @Lazy OrderService orderService,
            Producer producer,
            RestaurantService restaurantService
    ) {
        this.orderService = orderService;
        this.producer = producer;
        this.restaurantService = restaurantService;
    }

    public void pay(OrderRequest request, String orderId) {

        List<String> items = request
                .getRestaurantRequest()
                .getCarts()
                .stream()
                .map(OrderRequest.RestaurantRequest.Cart::getFoodId)
                .toList();

        PaymentRequest paymentRequest = PaymentRequest.
                builder()
                .userId(request.getUserId())
                .orderId(orderId)
                .emailId(request.getEmailId())
                .restaurantId(request.getRestaurantRequest().getRestaurantId())
                .providerId(request.getPaymentDetails().getProviderId())
                .upiId(request.getPaymentDetails().getUpiId())
                .debitCardNumber(request.getPaymentDetails().getDebitCardNumber())
                .creditCardNumber(request.getPaymentDetails().getCreditCardNumber())
                .paymentType(request.getPaymentDetails().getPaymentType())
                .amount(request.getPaymentDetails().getAmount())
                .items(items)
                .build();

        try {
            producer.queue(paymentInitiateTopic, paymentRequest);
        } catch (Exception e) {
            Order order = orderService.getOrder(orderId);
            order.setPaymentAction(Enum.PaymentAction.ERROR_OCCURRED);
            order.setPaymentMessage(e.getMessage());
            orderService.save(order);
            throw new KafkaException(e.getMessage());
        }
    }

    public void successEvent(Map<String, String> eventContext) {
        Order order = orderService.getOrder(eventContext.get(QueueKey.ORDER_ID_KEY));
        order.setPaymentAction(Enum.PaymentAction.SUCCESS);
        order.setPaymentMessage(eventContext.get(QueueKey.MESSAGE_KEY));
        order.setRestaurantAction(Enum.RestaurantAction.INITIATED);
        order.setRestaurantMessage(AppMessages.RESTAURANT_INITIATED);
        order.setDeliveryAction(Enum.DeliveryAction.YET_TO_START);
        order.setDeliveryMessage(AppMessages.SEARCHING_DELIVERY_AGENT);
        orderService.save(order);
        // Initiate restaurant service and delivery agent
        restaurantService.intimate(order);
    }

    public void processingEvent(Map<String, String> eventContext) {
        Order order = orderService.getOrder(eventContext.get(QueueKey.ORDER_ID_KEY));
        order.setPaymentAction(Enum.PaymentAction.PROCESSING);
        order.setPaymentMessage(eventContext.get(QueueKey.MESSAGE_KEY));
        orderService.save(order);
    }

    public void failedEvent(Map<String, String> eventContext) {
        Order order = orderService.getOrder(eventContext.get(QueueKey.ORDER_ID_KEY));
        order.setPaymentAction(Enum.PaymentAction.FAILED);
        order.setPaymentMessage(eventContext.get(QueueKey.MESSAGE_KEY));
        orderService.save(order);
    }

}
