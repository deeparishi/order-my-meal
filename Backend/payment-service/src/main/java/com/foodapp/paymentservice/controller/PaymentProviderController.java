package com.foodapp.paymentservice.controller;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.paymentservice.dto.response.PaymentProviderResponse;
import com.foodapp.paymentservice.service.PaymentProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/provider")
public class PaymentProviderController {

    @Autowired
    PaymentProviderService paymentModeService;

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<PaymentProviderResponse>> addMode(
            @RequestParam("provider") String provider,
            @RequestParam("type") String type
            ) {
        PaymentProviderResponse response = paymentModeService.addMode(provider, type);
        return ResponseEntity.ok(
                GenericResponse.success(HttpStatus.OK.value(),
                        AppMessages.PROVIDER_ADDED_MSG,
                        response
                )
        );
    }
}
