package com.foodapp.restaurantservice.repo.food;

import com.foodapp.restaurantservice.model.food.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends MongoRepository<Food, String> {

}
