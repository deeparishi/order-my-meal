package com.foodapp.restaurantservice.service.food;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.restaurantservice.dto.request.food.FoodRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodResponse;
import com.foodapp.restaurantservice.mapper.food.FoodMapper;
import com.foodapp.restaurantservice.model.food.Cuisine;
import com.foodapp.restaurantservice.model.food.Food;
import com.foodapp.restaurantservice.model.food.FoodCategory;
import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import com.foodapp.restaurantservice.repo.RestaurantRepo;
import com.foodapp.restaurantservice.repo.food.CuisineRepo;
import com.foodapp.restaurantservice.repo.food.FoodCategoryRepo;
import com.foodapp.restaurantservice.repo.food.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    FoodCategoryRepo foodCategoryRepo;

    @Autowired
    CuisineRepo cuisineRepo;

    @Autowired
    FoodRepo foodRepo;

    @Autowired
    FoodMapper foodMapper;

    public FoodResponse addFood(String restaurantId,
                                String cuisineId, FoodRequest foodRequest) {

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException(AppMessages.RESTAURANT_NOT_FOUND));

        FoodCategory foodCategory = foodCategoryRepo.findById(foodRequest.getFoodCategoryId())
                .orElseThrow(() -> new RuntimeException(AppMessages.CATEGORY_NOT_FOUND));

        Cuisine cuisine = cuisineRepo.findById(cuisineId)
                .orElseThrow(() -> new RuntimeException(AppMessages.CUISINE_NOT_FOUND));

        Food food = foodRepo.save(
                foodMapper.toEntity(restaurant.getId(),
                        foodCategory.getId(),
                        cuisine.getId(),
                        foodRequest
                )
        );

        return foodMapper.toResponse(food);
    }
}
