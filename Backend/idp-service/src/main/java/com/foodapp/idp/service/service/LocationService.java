package com.foodapp.idp.service.service;

import com.foodapp.idp.dto.request.app.ServiceLocationRequest;
import com.foodapp.idp.dto.response.ServiceLocationResponse;
import com.foodapp.idp.mapper.ServiceLocationMapper;
import com.foodapp.idp.model.ServiceLocation;
import com.foodapp.idp.repo.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    ServiceRepo serviceRepo;

    @Autowired
    ServiceLocationMapper locationMapper;

    public ServiceLocationResponse addService(ServiceLocationRequest locationRequest) {
        ServiceLocation location = locationMapper.toEntity(locationRequest);
        serviceRepo.save(location);
        return locationMapper.toResponse(location);
    }

}
