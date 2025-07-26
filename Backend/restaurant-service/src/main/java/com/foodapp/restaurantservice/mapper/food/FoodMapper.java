package com.foodapp.restaurantservice.mapper.food;

import com.foodapp.restaurantservice.dto.request.food.FoodRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodResponse;
import com.foodapp.restaurantservice.model.food.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "cuisineId", source = "cuisineId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Food toEntity(String restaurantId, String foodCategoryId, String cuisineId, FoodRequest foodRequest);

    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "cuisineId", source = "cuisineId")
    FoodResponse toResponse(Food food);

}
