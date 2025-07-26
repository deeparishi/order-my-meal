package com.foodapp.paymentservice.service;

import com.foodapp.paymentservice.dto.response.PaymentProviderResponse;
import com.foodapp.paymentservice.enums.Enum;
import com.foodapp.paymentservice.model.PaymentProvider;
import com.foodapp.paymentservice.repo.PaymentProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentProviderService {

    @Autowired
    PaymentProviderRepo paymentModeRepo;

    public PaymentProviderResponse addMode(String provider, String type) {

        Enum.PaymentType providerType = Enum.PaymentType.valueOf(type.toUpperCase());

        PaymentProvider paymentMode = paymentModeRepo.save(
                PaymentProvider.builder()
                        .provider(provider)
                        .providerType(providerType)
                        .status(Enum.Status.ACTIVE)
                        .isDeleted(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        return PaymentProviderResponse.builder()
                .id(paymentMode.getId())
                .provider(paymentMode.getProvider())
                .status(paymentMode.getStatus())
                .isDeleted(paymentMode.isDeleted())
                .createdAt(paymentMode.getCreatedAt())
                .updatedAt(paymentMode.getUpdatedAt())
                .build();
    }
}
