package com.foodapp.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class OrderResponse {

    private String emailId;
    private String userId;
    RestaurantResponse restaurantResponse;
    PaymentDetails paymentDetails;

    @Data
    public static class PaymentDetails {
        private String providerId;
        private String upiId;
        private String debitCardNumber;
        private String creditCardNumber;
        private String paymentType;
        private double amount;
    }

    @Data
    public static class RestaurantResponse {

        private String restaurantId;
        private String cuisineId;
        private String categoryId;
        private List<Cart> carts;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Cart {
            private String foodId;
            private int quantity;
        }
    }

}
