package com.foodapp.paymentservice.dto.response;

import com.foodapp.paymentservice.enums.Enum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentProviderResponse {

    private String id;
    private String provider;
    private boolean isDeleted;
    private Enum.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
