package com.foodapp.restaurantservice.model.food;


import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    private String id;

    @Indexed
    private String restaurantId;

    private String title;
    private String description;
    private String imageUrl;
    private Enum.OfferType type;
    private BigDecimal discountValue;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    private List<String> applicableFoodIds;
    private List<String> applicableMenuIds;
    private List<String> applicableCuisineIds;
    private List<String> applicableCategoryIds;
    private OfferAvailability availability;
    private String promoCode;
    private Integer usageLimit;
    private Integer currentUsageCount;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    @Data
    public static class OfferAvailability {
        private LocalDateTime validFrom;
        private LocalDateTime validUntil;
        private List<String> applicableDays;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}