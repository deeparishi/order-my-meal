package com.foodapp.orderservice.service;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.orderservice.dto.request.OrderRequest;
import com.foodapp.orderservice.enums.Enum;
import com.foodapp.orderservice.exception.NotFound;
import com.foodapp.orderservice.kafka.Producer;
import com.foodapp.orderservice.model.Order;
import com.foodapp.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {


    @Autowired
    Producer producer;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    OrderRepo orderRepo;

    public String foodOrder(OrderRequest request) {

        List<Order.Item> items = request
                .getRestaurantRequest()
                .getCarts()
                .stream()
                .map(cart -> Order.Item
                        .builder()
                        .foodId(cart.getFoodId())
                        .quantity(cart.getQuantity())
                        .categoryId(cart.getCategoryId())
                        .cuisineId(cart.getCuisineId())
                        .build())
                .toList();

        Order order = Order.builder()
                .orderId("Order-ID " + UUID.randomUUID().toString())
                .orderedByEmailId(request.getEmailId())
                .userId(request.getUserId())
                .restaurantId(request.getRestaurantRequest().getRestaurantId())
                .addressId(0)
                .serviceId(0)
                .amount(request.getPaymentDetails().getAmount())
                .items(items)
                .paymentAction(Enum.PaymentAction.INITIATED)
                .paymentMessage(AppMessages.PAYMENT_INITIATED)
                .deliveryAction(Enum.DeliveryAction.YET_TO_START)
                .deliveryMessage(AppMessages.DELIVERY_YET_TO_START)
                .restaurantAction(Enum.RestaurantAction.YET_TO_START)
                .restaurantMessage(AppMessages.RESTAURANT_YET_TO_START)
                .orderedAt(LocalDateTime.now())
                .build();

        orderRepo.save(order);
        paymentService.pay(request, order.getOrderId());

        return AppMessages.ORDER_IN_PROGRESS;
    }

    public Order getOrder(String orderId) {
        return orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new NotFound(AppMessages.ORDER_NOT_FOUND));
    }

    public void save(Order order) {
        orderRepo.save(order);
    }
}
