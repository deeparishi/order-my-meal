package com.foodapp.orderservice.service;

import com.foodapp.orderservice.enums.Enum;
import com.foodapp.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    public Order assignDeliveryPartner(Order order) {

        if (negativeActionFromRestaurant(order))
            return order;

        if (alreadyWorkingOnThatJob(order))
            return order;

        // TODO intimate delivery serve action
        order.setDeliveryMessage("Working on it");
        order.setDeliveryAction(Enum.DeliveryAction.YET_TO_START);
        return order;
    }

    private boolean alreadyWorkingOnThatJob(Order order) {
        return order.getDeliveryAction().equals(Enum.DeliveryAction.JOB_ACCEPTED) ||
                order.getDeliveryAction().equals(Enum.DeliveryAction.JOB_COMPLETED) ||
                order.getDeliveryAction().equals(Enum.DeliveryAction.JOB_PROCESSING) ||
                order.getDeliveryAction().equals(Enum.DeliveryAction.JOB_SEARCHING);
    }

    private boolean negativeActionFromRestaurant(Order order) {
        return order.getRestaurantAction().equals(Enum.RestaurantAction.ORDER_DECLINED) ||
                order.getRestaurantAction().equals(Enum.RestaurantAction.ORDER_PREPARING) ||
                order.getRestaurantAction().equals(Enum.RestaurantAction.ERROR_OCCURRED);
    }
}