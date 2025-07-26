package com.foodapp.idp.mapper;

import com.foodapp.common.dto.response.AddressResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.idp.dto.request.user.AddUserRequest;
import com.foodapp.idp.dto.request.user.AddressRequest;
import com.foodapp.idp.model.Address;
import com.foodapp.idp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "emailId", target = "emailId")
    @Mapping(source = "role", target = "role")
    User toEntity(AddUserRequest request);

    UserResponse toResponse(User user);

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    List<Address> toEntityList(List<AddressRequest> addressRequests);

    List<AddressResponse> toResponseList(List<Address> addresses);
}