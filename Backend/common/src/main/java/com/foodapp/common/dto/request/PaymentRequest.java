package com.foodapp.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String userId;
    private String orderId;
    private String emailId;
    private String restaurantId;
    private String providerId;
    private String upiId;
    private String debitCardNumber;
    private String creditCardNumber;
    private String paymentType;
    private double amount;
    private List<String> items;

}
