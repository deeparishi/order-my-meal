package com.foodapp.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class OrderRequest {

    private String emailId;
    private String userId;
    RestaurantRequest restaurantRequest;
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
    public static class RestaurantRequest {

        private String restaurantId;
        private List<Cart> carts;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Cart {
            private String cuisineId;
            private String categoryId;
            private String foodId;
            private String instructions;
            private String modifications;
            private int quantity;
        }
    }

}
