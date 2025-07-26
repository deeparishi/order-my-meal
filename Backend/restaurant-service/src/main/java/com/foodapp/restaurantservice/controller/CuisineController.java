package com.foodapp.restaurantservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.restaurantservice.dto.request.food.CuisineRequest;
import com.foodapp.restaurantservice.dto.response.food.CuisineResponse;
import com.foodapp.restaurantservice.service.food.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cuisine")
public class CuisineController {

    @Autowired
    CuisineService cuisineService;

    @PostMapping("/add/{restaurantId}/{categoryId}")
    public ResponseEntity<GenericResponse<CuisineResponse>> addCuisine(@PathVariable String restaurantId,
                                                                       @PathVariable String categoryId,
                                                                       @RequestBody CuisineRequest cuisineRequest) {
        CuisineResponse response = cuisineService.addCuisine(restaurantId, categoryId, cuisineRequest);
        return ResponseEntity.ok(
                GenericResponse.success(HttpStatus.OK.value(),
                        "Cuisine Added successfully!",
                        response)
        );
    }
}

