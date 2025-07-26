package com.foodapp.restaurantservice.model.restaurant;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "restaurant_revenue")
public class RestaurantRevenue {

    @Id
    String id;

    String orderId;
    String userId;
    String restaurantId;
    LocalDateTime orderedAt;
    boolean isCanceled;
    double earned;

}
