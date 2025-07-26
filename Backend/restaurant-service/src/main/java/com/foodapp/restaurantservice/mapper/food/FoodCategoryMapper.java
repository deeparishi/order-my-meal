package com.foodapp.restaurantservice.mapper.food;

import com.foodapp.restaurantservice.dto.request.food.FoodCategoryRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodCategoryResponse;
import com.foodapp.restaurantservice.model.food.FoodCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    FoodCategory toEntity(FoodCategoryRequest foodCategoryRequest, String restaurantId);

    FoodCategoryResponse toResponse(FoodCategory foodCategory);

}
