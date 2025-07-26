package com.foodapp.restaurantservice.service;

import com.foodapp.common.dto.request.RestaurantRequest;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.common.utils.QueueKey;
import com.foodapp.restaurantservice.dto.response.order.OrderStatusResponse;
import com.foodapp.restaurantservice.enums.Enum;
import com.foodapp.restaurantservice.exception.NotFound;
import com.foodapp.restaurantservice.kafka.Producer;
import com.foodapp.restaurantservice.model.order.FoodOrder;
import com.foodapp.restaurantservice.repo.order.FoodOrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    @Autowired
    FoodOrderRepo foodOrderRepo;

    @Value("${restaurant.producer.topic.name}")
    private String restaurantEventTopic;

    @Autowired
    Producer producer;

    public void newOrder(RestaurantRequest orderRequest) {

        List<FoodOrder.Cart> carts = orderRequest
                .getCarts()
                .stream()
                .map(cart -> FoodOrder.Cart
                        .builder()
                        .quantity(cart.getQuantity())
                        .instructions(cart.getInstructions())
                        .modifications(cart.getModifications())
                        .foodId(cart.getFoodId())
                        .cuisineId(cart.getCuisineId())
                        .categoryId(cart.getCategoryId())
                        .build())
                .toList();

        FoodOrder order = FoodOrder
                .builder()
                .orderId(orderRequest.getOrderId())
                .orderedAt(orderRequest.getOrderedAt())
                .userId(orderRequest.getUserId())
                .restaurantId(orderRequest.getRestaurantId())
                .carts(carts)
                .restaurantAmount(orderRequest.getRestaurantAmount())
                .orderStatus(Enum.OrderStatus.PENDING)
                .message("Waiting restaurant to confirm the order!")
                .build();

        foodOrderRepo.save(order);
    }

    public OrderStatusResponse orderActionByRestaurant(Enum.OrderStatus orderStatus,
                                        String orderId,
                                        String message) {

        FoodOrder order = foodOrderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new NotFound(AppMessages.ORDER_NOT_FOUND));

        order.setOrderStatus(orderStatus);
        order.setMessage(message != null && !message.isEmpty() ? message : "");
        foodOrderRepo.save(order);

        Map<String, String> evenContext = new HashMap<>();
        evenContext.put(QueueKey.USER_ID_KEY, order.getUserId());
        evenContext.put(QueueKey.ORDER_ID_KEY, order.getOrderId());
        evenContext.put(QueueKey.RESTAURANT_ID_KEY, order.getRestaurantId());
        evenContext.put(QueueKey.STATUS_KEY, orderStatus.name());
        evenContext.put(QueueKey.MESSAGE_KEY, message);

        producer.queue(restaurantEventTopic, evenContext);

        OrderStatusResponse response = OrderStatusResponse
                .builder()
                .orderId(orderId)
                .amountEarned(order.getRestaurantAmount())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();

        log.info("Order Action by restaurant on orderId {}, orderStatus {}", orderId, orderStatus.name());

        return response;
    }


}
