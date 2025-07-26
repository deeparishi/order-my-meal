package com.foodapp.restaurantservice.dto.request;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {


    private String name;
    private String description;
    private Address address;
    private ContactInfo contactInfo;
    private BusinessHours businessHours;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

    @Data
    public static class ContactInfo {
        private String phone;
        private String email;
        private String website;
    }

    @Data
    public static class BusinessHours {

        private Map<Enum.Weekday, TimeSlot> weeklyHours;

        @Data
        public static class TimeSlot {
            private LocalTime openTime;
            private LocalTime closeTime;
            private boolean isOpen;
        }
    }
}
