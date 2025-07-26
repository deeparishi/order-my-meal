package com.foodapp.idp.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String number;
    private String street;
    private String landMark;
    private String city;
    private Long zipcode;
    private String district;

}
