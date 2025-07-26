package com.foodapp.idp.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceLocationResponse {

    String serviceId;
    String city;
    String district;
    String state;
    String zipcode;
    LocalDateTime serviceFrom;

}
