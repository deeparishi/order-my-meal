package com.foodapp.paymentservice.dto.request;

import com.foodapp.paymentservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class UserAccountDetailsRequest {

    private String emailId;
    private Detail details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
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
