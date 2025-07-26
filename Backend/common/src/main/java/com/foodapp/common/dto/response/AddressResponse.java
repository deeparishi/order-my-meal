package com.foodapp.common.dto.response;

import lombok.Data;

@Data
public class AddressResponse {


    private String number;
    private String street;
    private String landMark;
    private String city;
    private Long zipcode;
    private String district;

}
