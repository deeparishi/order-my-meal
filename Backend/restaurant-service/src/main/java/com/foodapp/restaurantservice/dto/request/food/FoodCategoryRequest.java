package com.foodapp.restaurantservice.dto.request.food;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodCategoryRequest {

    private String name;
    private String description;
    private Enum.CategoryType type;
    private List<String> tags;
    private Enum.Status status;

}
