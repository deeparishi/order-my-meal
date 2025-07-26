package com.foodapp.restaurantservice.model.order;

import com.foodapp.restaurantservice.enums.Enum;
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
public class FoodOrder {

    @Id
    private String id;

    LocalDateTime orderedAt;
    String orderId;
    String userId;
    private String restaurantId;
    private List<Cart> carts;
    private double totalAmount;
    private double restaurantAmount;
    private Enum.OrderStatus orderStatus;
    private String message;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Cart {
        private String foodId;
        private String instructions;
        private String modifications;
        private int quantity;
        private String cuisineId;
        private String categoryId;
    }
}
