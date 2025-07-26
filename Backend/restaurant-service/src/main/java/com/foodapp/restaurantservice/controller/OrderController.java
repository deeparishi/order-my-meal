package com.foodapp.restaurantservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.restaurantservice.dto.response.order.OrderStatusResponse;
import com.foodapp.restaurantservice.enums.Enum;
import com.foodapp.restaurantservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/order")
public class OrderController {


    @Autowired
    OrderService orderService;

    @PostMapping("/status")
    public ResponseEntity<GenericResponse<OrderStatusResponse>> orderStatus(
            @RequestParam("status") Enum.OrderStatus orderStatus,
            @RequestParam("orderId") String orderId,
            @RequestParam(value = "message", required = false) String message) {
        OrderStatusResponse response = orderService.orderActionByRestaurant(orderStatus, orderId, message);
        return ResponseEntity.ok(
                GenericResponse.success(
                        HttpStatus.OK.value(),
                        "Status Changed successfully",
                        response
                )
        );
    }
}
