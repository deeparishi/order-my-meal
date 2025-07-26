package com.foodapp.orderservice.repo;

import com.foodapp.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {

    Optional<Order> findByOrderId(String orderId);
}
