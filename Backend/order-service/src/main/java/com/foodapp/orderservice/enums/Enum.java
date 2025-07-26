package com.foodapp.orderservice.enums;

public class Enum {

    public enum PaymentType {
        NET_BANKING,
        CREDIT_CARD,
        DEBIT_CARD,
        CASH,
        UPI_PAYMENT
    }

    public enum PaymentAction {
        INITIATED,
        PROCESSING,
        SUCCESS,
        FAILED,
        ERROR_OCCURRED
    }

    public enum RestaurantAction {
        YET_TO_START,
        INITIATED,
        ORDER_ACCEPTED,
        ORDER_PREPARING,
        ORDER_READY_TO_PICKUP,
        ORDER_COMPLETED,
        ORDER_DECLINED,
        ERROR_OCCURRED
    }

    public enum DeliveryAction {
        YET_TO_START,
        JOB_SEARCHING,
        JOB_ACCEPTED,
        JOB_PROCESSING,
        JOB_COMPLETED,
        JOB_DECLINED,
        JOB_FAILED,
        ERROR_OCCURRED
    }
}