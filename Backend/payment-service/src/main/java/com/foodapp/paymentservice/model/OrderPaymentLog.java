package com.foodapp.paymentservice.model;

import com.foodapp.paymentservice.enums.Enum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "order_payment_log")
public class OrderPaymentLog {

    @Id
    private String id;

    private String userId;
    private String emailId;
    private String restaurantId;
    private List<String> items;
    private String transactionId;
    private String providerId;
    private String orderId;
    private Enum.PaymentStatus paymentStatus;
    private String message;
    private LocalDateTime initiatedAt;
    private LocalDateTime updatedAt;
    private Double amount;
}
