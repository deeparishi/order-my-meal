package com.foodapp.restaurantservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.restaurantservice.dto.request.food.FoodCategoryRequest;
import com.foodapp.restaurantservice.dto.response.food.FoodCategoryResponse;
import com.foodapp.restaurantservice.service.food.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/category")
public class FoodCategoryController {

    @Autowired
    FoodCategoryService foodCategoryService;

    @PostMapping("/add/{restaurantId}")
    public ResponseEntity<GenericResponse<FoodCategoryResponse>> addCategory(@PathVariable String restaurantId,
                                                                             @RequestBody FoodCategoryRequest foodCategoryRequest) {

        FoodCategoryResponse response = foodCategoryService.addFoodCategory(restaurantId, foodCategoryRequest);

        return ResponseEntity.ok(
                GenericResponse.success(
                        HttpStatus.OK.value(),
                        "Category added to Restaurant",
                        response
                )
        );
    }

}
