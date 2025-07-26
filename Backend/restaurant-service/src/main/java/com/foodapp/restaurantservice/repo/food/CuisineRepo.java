package com.foodapp.restaurantservice.repo.food;

import com.foodapp.restaurantservice.model.food.Cuisine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepo extends MongoRepository<Cuisine, String> {
}
