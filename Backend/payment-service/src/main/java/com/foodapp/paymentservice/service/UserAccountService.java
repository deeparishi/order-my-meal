package com.foodapp.paymentservice.service;

import com.foodapp.common.dto.GenericResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.paymentservice.dto.request.UserAccountDetailsRequest;
import com.foodapp.paymentservice.dto.response.UserAccountDetailsResponse;
import com.foodapp.paymentservice.exception.FeignClientException;
import com.foodapp.paymentservice.exception.NotFound;
import com.foodapp.paymentservice.feign.IDPFeign;
import com.foodapp.paymentservice.model.AccountDetails;
import com.foodapp.paymentservice.model.PaymentProvider;
import com.foodapp.paymentservice.repo.PaymentProviderRepo;
import com.foodapp.paymentservice.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAccountService {


    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    PaymentProviderRepo paymentProviderRepo;

    @Autowired
    IDPFeign idpFeign;

    public UserAccountDetailsResponse accountDetails(UserAccountDetailsRequest request) {

        PaymentProvider provider = paymentProviderRepo.findById(request.getDetails().getProviderId())
                .orElseThrow(() -> new NotFound(AppMessages.PROVIDER_NOT_FOUND));

        Optional<AccountDetails> accountDetail = userAccountRepo.findByEmailId(request.getEmailId());

        if (accountDetail.isPresent()) {
            List<AccountDetails.Detail> details = accountDetail.get().getDetails();
            details.add(
                    new AccountDetails.Detail(
                            provider.getId(),
                            request.getDetails().getUpiId(),
                            request.getDetails().getDebitCardNumber(),
                            request.getDetails().getCreditCardNumber(),
                            request.getDetails().getBankingId(),
                            request.getDetails().getAmountBalance(),
                            request.getDetails().getPaymentType()
                    )
            );
            userAccountRepo.save(accountDetail.get());
        } else {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setEmailId(request.getEmailId());
            accountDetails.setCreatedAt(LocalDateTime.now());
            accountDetails.setUpdatedAt(LocalDateTime.now());
            List<AccountDetails.Detail> details = new ArrayList<>();
            details.add(
                    new AccountDetails.Detail(
                            provider.getId(),
                            request.getDetails().getUpiId(),
                            request.getDetails().getDebitCardNumber(),
                            request.getDetails().getCreditCardNumber(),
                            request.getDetails().getBankingId(),
                            request.getDetails().getAmountBalance(),
                            request.getDetails().getPaymentType()
                    )
            );
            accountDetails.setDetails(details);
            accountDetail = Optional.of(userAccountRepo.save(accountDetails));
        }


        return UserAccountDetailsResponse.builder()
                .id(accountDetail.get().getId())
                .emailId(accountDetail.get().getEmailId())
                .createdAt(accountDetail.get().getCreatedAt())
                .updatedAt(accountDetail.get().getUpdatedAt())
                .details(toResponse(accountDetail.get().getDetails()))
                .build();
    }

    private List<UserAccountDetailsResponse.Detail> toResponse(List<AccountDetails.Detail> accDetails) {

        return accDetails
                .stream()
                .map(accDetail -> UserAccountDetailsResponse.Detail
                        .builder()
                        .providerId(accDetail.getProviderId())
                        .upiId(accDetail.getUpiId())
                        .creditCardNumber(accDetail.getCreditCardNumber())
                        .debitCardNumber(accDetail.getDebitCardNumber())
                        .bankingId(accDetail.getBankingId())
                        .amountBalance(accDetail.getAmountBalance())
                        .paymentType(accDetail.getPaymentType())
                        .build())
                .toList();
    }

    public ResponseEntity<GenericResponse<UserResponse>> get(int id) {
        try {
            return idpFeign.getUserDetails(id);
        } catch (Exception e) {
            throw new FeignClientException(e.getMessage());
        }
    }
}
