package com.foodapp.restaurantservice.enums;

public class Enum {

    public enum FoodType {
        VEG,
        NON_VEG
    }


    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Weekday {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
    }


    public enum OfferType {
        PERCENTAGE, FIXED_AMOUNT, BUY_ONE_GET_ONE, FREE_DELIVERY, COMBO_DEAL
    }

    public enum CategoryType {
        FOOD_TYPE, FOOD_CATEGORY, MENU_TYPE
    }

    public enum Status {
        ACTIVE, INACTIVE, DELETED
    }

    public enum OrderStatus {
        PENDING, ACCEPTED, REJECTED, READY_TO_PICKUP, PREPARING, COMPLETED
    }
}
