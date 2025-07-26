package com.foodapp.idp.dto.request.app;

import lombok.Data;

@Data
public class ServiceLocationRequest {

    String city;
    String district;
    String state;
    String zipcode;
}
