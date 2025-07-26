package com.foodapp.restaurantservice.repo;

import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends MongoRepository<Restaurant, String> {
}
