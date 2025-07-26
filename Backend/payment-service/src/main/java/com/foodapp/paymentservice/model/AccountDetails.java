package com.foodapp.paymentservice.model;

import com.foodapp.paymentservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_bank_account")
public class AccountDetails {

    @Id
    private String id;
    private String emailId;
    private List<Detail> details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
        Enum.PaymentType paymentType;
    }
}
