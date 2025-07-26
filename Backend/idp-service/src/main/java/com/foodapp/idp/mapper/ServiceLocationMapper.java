package com.foodapp.idp.mapper;

import com.foodapp.idp.dto.request.app.ServiceLocationRequest;
import com.foodapp.idp.dto.response.ServiceLocationResponse;
import com.foodapp.idp.model.ServiceLocation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceLocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "serviceId", ignore = true)
    @Mapping(target = "serviceFrom", ignore = true)
   ServiceLocation toEntity(ServiceLocationRequest request);

    @AfterMapping
    default void afterMapping(@MappingTarget ServiceLocation location) {
        location.setServiceId("SRV-" + UUID.randomUUID());
        location.setServiceFrom(LocalDateTime.now());
    }

    ServiceLocationResponse toResponse(ServiceLocation location);
}
