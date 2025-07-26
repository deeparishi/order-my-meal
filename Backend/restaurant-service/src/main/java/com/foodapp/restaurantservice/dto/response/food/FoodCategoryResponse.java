package com.foodapp.restaurantservice.dto.response.food;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodCategoryResponse {

    private String id;
    private String restaurantId;

    private String name;
    private String description;
    private Enum.CategoryType type;
    private List<String> tags;
    private Enum.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
