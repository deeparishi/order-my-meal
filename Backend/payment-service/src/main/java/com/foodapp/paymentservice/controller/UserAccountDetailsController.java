package com.foodapp.paymentservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.paymentservice.dto.request.UserAccountDetailsRequest;
import com.foodapp.paymentservice.dto.response.UserAccountDetailsResponse;
import com.foodapp.paymentservice.service.UserAccountService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-account")
public class UserAccountDetailsController {

    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/get-user-by-id/{id}")
    @Retry(name = "get", fallbackMethod = "idpFallback")
    private ResponseEntity<GenericResponse<UserResponse>> getUser(@PathVariable int id) {
        return userAccountService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<UserAccountDetailsResponse>> addUserAccount(@RequestBody UserAccountDetailsRequest accountDetailsRequest) {
        UserAccountDetailsResponse response = userAccountService.accountDetails(accountDetailsRequest);
        return ResponseEntity.ok(
                GenericResponse.success(
                        HttpStatus.OK.value(),
                        AppMessages.ACCOUNT_ADDED_MSG,
                        response
                )
        );
    }


    public ResponseEntity<GenericResponse<Object>> idpFallback(Throwable throwable) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        HttpStatus.OK.value(),
                        "Reached to Fallback",
                        throwable.getMessage()
                )
        );
    }
}
