package com.foodapp.restaurantservice.service.food;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.restaurantservice.dto.request.food.FoodCategoryRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodCategoryResponse;
import com.foodapp.restaurantservice.exception.NotFound;
import com.foodapp.restaurantservice.mapper.food.FoodCategoryMapper;
import com.foodapp.restaurantservice.model.food.FoodCategory;
import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import com.foodapp.restaurantservice.repo.RestaurantRepo;
import com.foodapp.restaurantservice.repo.food.FoodCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FoodCategoryService {

    @Autowired
    FoodCategoryRepo foodCategoryRepo;

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    FoodCategoryMapper foodCategoryMapper;

    public FoodCategoryResponse addFoodCategory(String restaurantId, FoodCategoryRequest foodCategoryRequest) {

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFound(AppMessages.RESTAURANT_NOT_FOUND));

        FoodCategory foodCategory = foodCategoryMapper.toEntity(foodCategoryRequest, restaurant.getId());

        foodCategoryRepo.save(foodCategory);

        return foodCategoryMapper.toResponse(foodCategory);
    }
}
