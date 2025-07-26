package com.foodapp.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class FallbackController {

    @RequestMapping("/connect-support")
    public ResponseEntity<String> fallback(ServerWebExchange exchange,
                                           @RequestParam(required = false) String errorType) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("The app is currently unavailable. Please try again after sometime!");
    }
}
