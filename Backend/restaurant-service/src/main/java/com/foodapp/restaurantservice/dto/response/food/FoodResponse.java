package com.foodapp.restaurantservice.dto.response.food;

import com.foodapp.restaurantservice.model.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

    private String id;
    private String restaurantId;
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
