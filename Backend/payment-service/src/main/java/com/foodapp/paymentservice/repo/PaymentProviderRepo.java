package com.foodapp.paymentservice.repo;

import com.foodapp.paymentservice.model.PaymentProvider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProviderRepo extends MongoRepository<PaymentProvider, String> {

}
