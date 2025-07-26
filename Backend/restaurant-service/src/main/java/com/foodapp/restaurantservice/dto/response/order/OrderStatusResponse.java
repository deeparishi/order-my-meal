package com.foodapp.restaurantservice.dto.response.order;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusResponse {

    private String orderId;
    private Enum.OrderStatus orderStatus;
    private double amountEarned;
    private String message;

}
