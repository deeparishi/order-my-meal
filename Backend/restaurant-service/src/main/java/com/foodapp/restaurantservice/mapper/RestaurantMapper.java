package com.foodapp.restaurantservice.mapper;

import com.foodapp.restaurantservice.dto.request.RestaurantRequest;
import com.foodapp.restaurantservice.dto.response.RestaurantResponse;
import com.foodapp.restaurantservice.model.restaurant.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Restaurant toEntity(RestaurantRequest restaurantRequest);

    RestaurantResponse toResponse(Restaurant restaurant);

    RestaurantResponse.Address toResponseAddress(Restaurant.Address address);
    Restaurant.Address toEntityAddress(RestaurantRequest.Address address);

    RestaurantResponse.ContactInfo toResponseContactInfo(Restaurant.ContactInfo contactInfo);
    Restaurant.ContactInfo toEntityContactInfo(RestaurantRequest.ContactInfo contactInfo);

    RestaurantResponse.BusinessHours toResponseBusinessHours(Restaurant.BusinessHours businessHours);
    Restaurant.BusinessHours toEntityBusinessHours(RestaurantRequest.BusinessHours businessHours);

    @Mapping(target = "openTime", source = "openTime")
    @Mapping(target = "closeTime", source = "closeTime")
//    @Mapping(target = "isOpen", source = "isOpen")
    RestaurantResponse.BusinessHours.TimeSlot toResponseTimeSlot(Restaurant.BusinessHours.TimeSlot timeSlot);

    @Mapping(target = "openTime", source = "openTime")
    @Mapping(target = "closeTime", source = "closeTime")
//    @Mapping(target = "isOpen", source = "isOpen") // Map isOpen from TimeSlot
    Restaurant.BusinessHours.TimeSlot toEntityTimeSlot(RestaurantRequest.BusinessHours.TimeSlot timeSlot);


    }