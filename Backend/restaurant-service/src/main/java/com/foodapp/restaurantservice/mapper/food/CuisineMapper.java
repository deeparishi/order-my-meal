package com.foodapp.restaurantservice.mapper.food;

import com.foodapp.restaurantservice.dto.request.food.CuisineRequest;
import com.foodapp.restaurantservice.dto.response.food.CuisineResponse;
import com.foodapp.restaurantservice.model.food.Cuisine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CuisineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Cuisine toEntity(CuisineRequest cuisineRequest, String restaurantId, String categoryId);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CuisineResponse toResponse(Cuisine cuisine);

}
