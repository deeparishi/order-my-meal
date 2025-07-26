package com.foodapp.paymentservice.model;

import com.foodapp.paymentservice.enums.Enum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "payment_provider")
@Builder
public class PaymentProvider {

    @Id
    private String id;
    private String provider;
    private boolean isDeleted;
    private Enum.Status status;
    private Enum.PaymentType providerType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
