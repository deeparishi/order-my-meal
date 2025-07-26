package com.foodapp.restaurantservice.repo.food;

import com.foodapp.restaurantservice.model.food.FoodCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodCategoryRepo extends MongoRepository<FoodCategory, String> {

}
