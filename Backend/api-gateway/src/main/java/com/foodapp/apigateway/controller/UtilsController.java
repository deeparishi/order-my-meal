package com.foodapp.apigateway.controller;

import com.foodapp.apigateway.filter.FilterUtility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/utils")
public class UtilsController {

    @GetMapping("/get-correlation-id")
    public String getCorrelationId() {
        return FilterUtility.CORRELATION_ID;
    }

}
