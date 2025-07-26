package com.foodapp.paymentservice.dto.response;

import com.foodapp.paymentservice.enums.Enum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PaymentResponse {

    private String userId;
    private String emailId;
    private String restaurantId;
    private String upiId;
    private String debitCardNumber;
    private String creditCardNumber;
    private Enum.PaymentType paymentType;
    private double amount;
    private List<String> items;
    private String transactionId;
    private String providerId;
    private String orderId;
    private Enum.PaymentStatus paymentStatus;
    private String message;
    private LocalDateTime initiatedAt;
    private LocalDateTime updatedAt;
}
