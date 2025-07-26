package com.foodapp.paymentservice.service;

import com.foodapp.common.dto.request.PaymentRequest;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.common.utils.QueueKey;
import com.foodapp.paymentservice.enums.Enum;
import com.foodapp.paymentservice.exception.KafkaException;
import com.foodapp.paymentservice.exception.NotFound;
import com.foodapp.paymentservice.kafka.Producer;
import com.foodapp.paymentservice.model.AccountDetails;
import com.foodapp.paymentservice.model.OrderPaymentLog;
import com.foodapp.paymentservice.model.PaymentProvider;
import com.foodapp.paymentservice.repo.OrderPaymentLogRepo;
import com.foodapp.paymentservice.repo.PaymentProviderRepo;
import com.foodapp.paymentservice.repo.UserAccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    OrderPaymentLogRepo orderPaymentLogRepo;

    @Autowired
    PaymentProviderRepo paymentProviderRepo;

    @Autowired
    Producer producer;

    @Value("${payment.producer.topic.processing.name}")
    private String paymentProcessing;

    @Value("${payment.producer.topic.success.name}")
    private String paymentSuccess;

    @Value("${payment.producer.topic.failed.name}")
    private String paymentFailed;

    public void initiatePayment(PaymentRequest request) {

        Map<String, String> processEvent = new HashMap<>();
        processEvent.put(QueueKey.USER_ID_KEY, request.getUserId());
        processEvent.put(QueueKey.EMAIL_ID_KEY, request.getEmailId());
        processEvent.put(QueueKey.ORDER_ID_KEY, request.getOrderId());
        processEvent.put(QueueKey.RESTAURANT_ID_KEY, request.getRestaurantId());
        processEvent.put(QueueKey.MESSAGE_KEY, AppMessages.PAYMENT_IN_PROGRESS);

        paymentProcessEvent(processEvent);

        Optional<PaymentProvider> providerOpt = paymentProviderRepo.findById(request.getProviderId());
        Optional<AccountDetails> userAccountOpt = userAccountRepo.findByEmailId(request.getEmailId());

        if (isValidRequest(providerOpt, userAccountOpt, request, processEvent)) {
            PaymentProvider provider = providerOpt.get();
            AccountDetails userAccount = userAccountOpt.get();

            AccountDetails.Detail detail = userAccount.getDetails()
                    .stream()
                    .filter(d -> d.getProviderId().equals(provider.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFound("Detail not found after validation".concat(" and").concat(AppMessages.PAYMENT_FAILED)));

            double newBalance = detail.getAmountBalance() - request.getAmount();
            detail.setAmountBalance(newBalance);

            processEvent.put(QueueKey.MESSAGE_KEY, AppMessages.PAYMENT_SUCCESS);
            paymentSuccessEvent(processEvent);

            userAccountRepo.save(userAccount);
            logOrderPayment(request, processEvent.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.COMPLETED);
            log.info("Payment successful for orderId = {}, userId = {}", request.getOrderId(), request.getUserId());
        }
    }

    private void logOrderPayment(PaymentRequest request, String message, Enum.PaymentStatus status) {
        OrderPaymentLog log = OrderPaymentLog
                .builder()
                .userId(request.getUserId())
                .emailId(request.getEmailId())
                .restaurantId(request.getRestaurantId())
                .items(request.getItems())
                .transactionId(UUID.randomUUID().toString())
                .providerId(request.getProviderId())
                .orderId(request.getOrderId())
                .paymentStatus(status)
                .message(message)
                .initiatedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .amount(request.getAmount())
                .build();
        orderPaymentLogRepo.save(log);
    }

    private boolean isValidRequest(Optional<PaymentProvider> providerOpt,
                                   Optional<AccountDetails> userAccountOpt,
                                   PaymentRequest request,
                                   Map<String, String> eventContext) {

        if (providerOpt.isEmpty()) {
            eventContext.put(QueueKey.MESSAGE_KEY, AppMessages.PROVIDER_NOT_FOUND);
            paymentFailedEvent(eventContext);
            logOrderPayment(request, eventContext.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.FAILED);
            return false;
        }

        if (userAccountOpt.isEmpty()) {
            eventContext.put(QueueKey.MESSAGE_KEY, AppMessages.USER_ACCOUNT_NOT_FOUND);
            paymentFailedEvent(eventContext);
            logOrderPayment(request, eventContext.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.FAILED);
            return false;
        }

        AccountDetails userAccount = userAccountOpt.get();
        PaymentProvider provider = providerOpt.get();

        Map<String, AccountDetails.Detail> detailsByProvider = userAccount.getDetails()
                .stream()
                .collect(Collectors.toMap(
                        AccountDetails.Detail::getProviderId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        if (!detailsByProvider.containsKey(provider.getId())) {
            eventContext.put(QueueKey.MESSAGE_KEY, "User does not have account with this provider".concat(" and").concat(AppMessages.PAYMENT_FAILED));
            logOrderPayment(request, eventContext.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.FAILED);
            paymentFailedEvent(eventContext);
            return false;
        }

        AccountDetails.Detail detail = detailsByProvider.get(provider.getId());

        if (!detail.getPaymentType().name().equalsIgnoreCase(request.getPaymentType())) {
            eventContext.put(QueueKey.MESSAGE_KEY, AppMessages.PAYMENT_TYPE_MISMATCH.concat(" and").concat(AppMessages.PAYMENT_FAILED));
            logOrderPayment(request, eventContext.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.FAILED);
            paymentFailedEvent(eventContext);
            return false;
        }

        if (detail.getAmountBalance() < request.getAmount()) {
            eventContext.put(QueueKey.MESSAGE_KEY, AppMessages.INSUFFICIENT_BALANCE.concat(" and").concat(AppMessages.PAYMENT_FAILED));
            logOrderPayment(request, eventContext.get(QueueKey.MESSAGE_KEY), Enum.PaymentStatus.FAILED);
            paymentFailedEvent(eventContext);
            return false;
        }

        return true;
    }


    private void paymentProcessEvent(Map<String, String> event) {
        try {
            producer.queue(paymentProcessing, event);
        } catch (Exception e) {
            throw new KafkaException(e.getMessage());
        }
    }

    private void paymentFailedEvent(Map<String, String> event) {
        try {
            producer.queue(paymentFailed, event);
        } catch (Exception e) {
            throw new KafkaException(e.getMessage());
        }
    }

    private void paymentSuccessEvent(Map<String, String> event) {
        try {
            producer.queue(paymentSuccess, event);
        } catch (Exception e) {
            throw new KafkaException(e.getMessage());
        }
    }


}
