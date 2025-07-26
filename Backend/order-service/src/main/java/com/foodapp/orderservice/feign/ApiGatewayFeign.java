package com.foodapp.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "API-GATEWAY")
public interface ApiGatewayFeign {

    @GetMapping("/v1/utils/get-correlation-id")
    String getCorrelationId();
}