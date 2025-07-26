package com.foodapp.orderservice.model;

import com.foodapp.orderservice.dto.request.OrderRequest;
import com.foodapp.orderservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "food_order")
public class Order {

    @Id
    String id;

    private String orderId;
    private String orderedByEmailId;
    private String userId;
    private String restaurantId;
    private int addressId;
    private int serviceId;
    private double amount;
    private Enum.PaymentAction paymentAction;
    private String paymentMessage;
    private Enum.DeliveryAction deliveryAction;
    private String deliveryMessage;
    private Enum.RestaurantAction restaurantAction;
    private String restaurantMessage;
    private LocalDateTime orderedAt;
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Item {
        private String foodId;
        private int quantity;
        private String instructions;
        private String modifications;
        private String cuisineId;
        private String categoryId;
    }
}
