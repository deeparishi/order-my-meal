package com.foodapp.restaurantservice.model.food;

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

@Document(collection = "combos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combo {
    @Id
    private String id;

    @Indexed
    private String restaurantId;

    private String name;
    private String description;
    private String imageUrl;
    private List<ComboItem> items;
    private BigDecimal originalPrice;
    private BigDecimal comboPrice;
    private BigDecimal savingsAmount;
    private List<String> categoryIds;
    private boolean isActive;
    private ComboAvailability availability;
    private int preparationTime;
    private ComboRating rating;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class ComboItem {
        private String foodId;
        private String foodName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private List<String> customizations;
    }

    @Data
    public static class ComboAvailability {
        private LocalDateTime availableFrom;
        private LocalDateTime availableUntil;
        private List<String> availableDays;
        private LocalTime startTime;
        private LocalTime endTime;
        private Integer maxQuantityPerDay;
        private Integer currentQuantityOrdered;
    }

    @Data
    public static class ComboRating {
        private Double averageRating;
        private Integer totalReviews;
        private LocalDateTime lastUpdated;
    }
}