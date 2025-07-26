package com.foodapp.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {

    private String restaurantId;
    private List<Cart> carts;
    private double totalAmount;
    private double restaurantAmount;
    private LocalDateTime orderedAt;
    private String orderId;
    private String userId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Cart {
        private String foodId;
        private String instructions;
        private String modifications;
        private String cuisineId;
        private String categoryId;
        private int quantity;
    }
}
