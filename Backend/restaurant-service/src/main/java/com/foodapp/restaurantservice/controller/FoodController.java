package com.foodapp.restaurantservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.restaurantservice.dto.request.food.FoodRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodResponse;
import com.foodapp.restaurantservice.service.food.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<FoodResponse>> addFood(@RequestParam("restaurantId") String restaurantId,
                                                                 @RequestParam("cuisineId") String cuisineId,
                                                                 @RequestBody FoodRequest foodRequest) {
        FoodResponse response = foodService.addFood(restaurantId, cuisineId, foodRequest);

        return ResponseEntity.ok(
                GenericResponse.success(
                        HttpStatus.OK.value(),
                        "Food added to restaurant",
                        response
                )
        );
    }


}
