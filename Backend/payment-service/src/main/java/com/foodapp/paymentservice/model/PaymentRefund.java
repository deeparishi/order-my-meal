package com.foodapp.paymentservice.model;

import com.foodapp.paymentservice.enums.Enum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "payment_refund")
public class PaymentRefund {

    @Id
    private String id;

    private String transactionId;
    private String userId;
    private String emailId;
    private String paymentId;
    private Double refundAmount;
    private Enum.RefundStatus refundStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
