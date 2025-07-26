package com.foodapp.idp.controller;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.dto.GenericResponse;
import com.foodapp.idp.dto.request.app.ServiceLocationRequest;
import com.foodapp.idp.dto.response.ServiceLocationResponse;
import com.foodapp.idp.service.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/location-service")
public class LocationServiceController {

    @Autowired
    LocationService locationService;


    @PostMapping("/add-service")
    public ResponseEntity<GenericResponse<ServiceLocationResponse>> addAppService(
            @RequestBody ServiceLocationRequest locationRequest) {
        ServiceLocationResponse response = locationService.addService(locationRequest);
        return ResponseEntity.ok(
                GenericResponse.success(200, AppMessages.LOCATION_ADDED_MSG, response)
        );
    }

}
