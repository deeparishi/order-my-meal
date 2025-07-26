package com.foodapp.paymentservice.repo;

import com.foodapp.paymentservice.model.OrderPaymentLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentLogRepo extends MongoRepository<OrderPaymentLog, String> {

}
