package com.foodapp.restaurantservice.service.food;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.restaurantservice.dto.request.food.CuisineRequest;
import com.foodapp.restaurantservice.dto.response.food.CuisineResponse;
import com.foodapp.restaurantservice.exception.NotFound;
import com.foodapp.restaurantservice.mapper.food.CuisineMapper;
import com.foodapp.restaurantservice.model.food.Cuisine;
import com.foodapp.restaurantservice.model.food.FoodCategory;
import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import com.foodapp.restaurantservice.repo.RestaurantRepo;
import com.foodapp.restaurantservice.repo.food.CuisineRepo;
import com.foodapp.restaurantservice.repo.food.FoodCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuisineService {

    @Autowired
    CuisineRepo cuisineRepo;

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    FoodCategoryRepo foodCategoryRepo;

    @Autowired
    CuisineMapper cuisineMapper;

    public CuisineResponse addCuisine(String restaurantId,
                                      String categoryId,
                                      CuisineRequest cuisineRequest) {

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFound(AppMessages.RESTAURANT_NOT_FOUND));

        FoodCategory foodCategory = foodCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFound(AppMessages.CATEGORY_NOT_FOUND));

        Cuisine cuisine = cuisineRepo.save(
                cuisineMapper.toEntity(cuisineRequest, restaurant.getId(), foodCategory.getId())
        );

        return cuisineMapper.toResponse(cuisine);
    }
}
