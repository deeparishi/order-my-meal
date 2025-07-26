package com.foodapp.orderservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.orderservice.dto.request.OrderRequest;
import com.foodapp.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<String>> orderFood(@RequestBody OrderRequest request) throws ExecutionException, InterruptedException {
        String response = orderService.foodOrder(request);
        return ResponseEntity.ok(
                GenericResponse.success(200, AppMessages.FOOD_ORDERED_MSG, response)
        );
    }

}
