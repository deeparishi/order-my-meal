package com.foodapp.paymentservice.dto.response;

import com.foodapp.paymentservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserAccountDetailsResponse {

    private String id;
    private String emailId;
    private List<Detail> details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Detail {
        String providerId;
        String upiId;
        String debitCardNumber;
        String creditCardNumber;
        String bankingId;
        Double amountBalance;
        private Enum.PaymentType paymentType;
    }
}
