package com.foodapp.orderservice.service;

import com.foodapp.common.dto.request.RestaurantRequest;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.common.utils.QueueKey;
import com.foodapp.orderservice.enums.Enum;
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
public class RestaurantService {

    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final Producer producer;

    public static final double appCommission = 25.50;

    @Value("${order.producer.topic.restaurant.name}")
    private String restaurantTopic;

    public RestaurantService(@Lazy OrderService orderService,
                             @Lazy DeliveryService deliveryService,
                             Producer producer) {
        this.orderService = orderService;
        this.producer = producer;
        this.deliveryService = deliveryService;
    }

    public void intimate(Order order) {

        List<RestaurantRequest.Cart> carts = order
                .getItems()
                .stream()
                .map(cart -> RestaurantRequest.Cart.builder()
                        .quantity(cart.getQuantity())
                        .foodId(cart.getFoodId())
                        .instructions(cart.getInstructions())
                        .categoryId(cart.getCategoryId())
                        .cuisineId(cart.getCuisineId())
                        .modifications(cart.getModifications())
                        .build()
                )
                .toList();

        RestaurantRequest dto = RestaurantRequest
                .builder()
                .restaurantId(order.getRestaurantId())
                .restaurantAmount(order.getAmount() - getCommission(order.getAmount()))
                .totalAmount(order.getAmount())
                .userId(order.getUserId())
                .orderId(order.getOrderId())
                .orderedAt(order.getOrderedAt())
                .carts(carts)
                .build();
        try {
            producer.queue(restaurantTopic, dto);
        } catch (Exception e) {
            log.info("Error occurred while queueing the data into topic {}", restaurantTopic);
            log.info("Ërror occurred is {}", e.getMessage());
            order.setRestaurantAction(Enum.RestaurantAction.ERROR_OCCURRED);
            order.setRestaurantMessage(e.getMessage());
            return;
        }

        order.setRestaurantMessage(AppMessages.RESTAURANT_INITIATED);
        order.setRestaurantAction(Enum.RestaurantAction.INITIATED);
        orderService.save(order);
    }

    public double getCommission(double amount) {
        return (appCommission / 100) * amount;
    }

    public void responseOfRestaurantService(Map<String, String> eventContext) {

        Order order = orderService.getOrder(eventContext.get(QueueKey.ORDER_ID_KEY));
        Enum.RestaurantAction action = convertAction(eventContext.get(QueueKey.STATUS_KEY));
        order.setRestaurantAction(action);
        order.setRestaurantMessage(eventContext.get(QueueKey.MESSAGE_KEY));
        order = deliveryService.assignDeliveryPartner(order);
        orderService.save(order);
    }

    private Enum.RestaurantAction convertAction(String status) {
        return switch (status) {
            case "ACCEPTED" -> Enum.RestaurantAction.ORDER_ACCEPTED;
            case "PREPARING" -> Enum.RestaurantAction.ORDER_PREPARING;
            case "REJECTED" -> Enum.RestaurantAction.ORDER_DECLINED;
            case "COMPLETED" -> Enum.RestaurantAction.ORDER_COMPLETED;
            case "READY_TO_PICKUP" -> Enum.RestaurantAction.ORDER_READY_TO_PICKUP;
            default -> null;
        };
    }
}
