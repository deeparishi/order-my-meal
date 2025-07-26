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

@Document(collection = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    private String id;

    @Indexed
    private String restaurantId;

    @Indexed
    private String cuisineId;

    private String name;
    private String description;
    private BigDecimal basePrice;
    private List<String> ingredients;
    private List<String> allergens;
    private NutritionInfo nutritionInfo;
    private String foodCategoryId;
    private List<String> tags;
    private boolean isAvailable;
    private FoodAvailability availability;
    private int preparationTime;
    private FoodRating rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class NutritionInfo {
        private Double calories;
        private Double protein;
        private Double carbs;
        private Double fat;
        private Double fiber;
        private Double sugar;
        private Double sodium;
    }

    @Data
    public static class FoodAvailability {
        private LocalDateTime availableFrom;
        private LocalDateTime availableUntil;
        private List<String> availableDays;
        private LocalTime startTime;
        private LocalTime endTime;
        private Integer maxQuantityPerDay;
        private Integer currentQuantityOrdered;
    }

    @Data
    public static class FoodRating {
        private Double averageRating;
        private Integer totalReviews;
        private LocalDateTime lastUpdated;
    }
}
