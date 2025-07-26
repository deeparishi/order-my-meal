package com.foodapp.restaurantservice.repo.order;

import com.foodapp.restaurantservice.model.order.FoodOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FoodOrderRepo extends MongoRepository<FoodOrder, String> {
    Optional<FoodOrder> findByOrderId(String orderId);
}
