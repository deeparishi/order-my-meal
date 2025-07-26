package com.foodapp.restaurantservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.restaurantservice.dto.request.RestaurantRequest;
import com.foodapp.restaurantservice.dto.response.RestaurantResponse;
import com.foodapp.restaurantservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<RestaurantResponse>> addRestaurant(
            @RequestBody RestaurantRequest restaurantRequest) {
        RestaurantResponse response = restaurantService.addRestaurant(restaurantRequest);
        return ResponseEntity.ok(
                GenericResponse.success(HttpStatus.OK.value(),
                        "Restaurant Added successfully!",
                        response)
        );
    }
}
