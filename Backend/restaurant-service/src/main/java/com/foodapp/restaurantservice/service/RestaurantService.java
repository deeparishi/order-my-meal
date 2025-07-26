package com.foodapp.restaurantservice.service;

import com.foodapp.restaurantservice.dto.request.RestaurantRequest;
import com.foodapp.restaurantservice.dto.response.RestaurantResponse;
import com.foodapp.restaurantservice.mapper.RestaurantMapper;
import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import com.foodapp.restaurantservice.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    RestaurantMapper restaurantMapper;

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantRequest);
        restaurantRepo.save(restaurant);
        return restaurantMapper.toResponse(restaurant);
    }
}
