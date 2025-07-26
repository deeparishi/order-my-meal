package com.foodapp.paymentservice.enums;

public class Enum {

    public enum PaymentStatus {
        REVOKED,
        INITIATED,
        PROCESSING,
        COMPLETED,
        FAILED,
        REFUND
    }

    public enum PaymentType {
        NET_BANKING,
        CREDIT_CARD,
        DEBIT_CARD,
        CASH,
        UPI_PAYMENT
    }

    public enum Status {
        ACTIVE,
        IN_ACTIVE
    }

    public enum RefundStatus {
        INITIATED,
        PROCESSING,
        COMPLETED,
        FAILED,
        REVOKED
    }
}
