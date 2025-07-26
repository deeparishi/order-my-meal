package com.foodapp.paymentservice.feign;


import com.foodapp.common.dto.GenericResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.paymentservice.config.AppConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "IDP-SERVICE", configuration = AppConfig.class)
public interface IDPFeign {

    @GetMapping("/idp/v1/user/get-user-by-id/{userId}")
    ResponseEntity<GenericResponse<UserResponse>> getUserDetails(@PathVariable("userId") int userId);
}