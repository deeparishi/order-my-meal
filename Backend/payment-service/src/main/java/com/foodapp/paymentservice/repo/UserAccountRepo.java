package com.foodapp.paymentservice.repo;

import com.foodapp.paymentservice.model.AccountDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserAccountRepo extends MongoRepository<AccountDetails, String> {
    Optional<AccountDetails> findByEmailId(String emailId);
}
