package com.foodapp.common.dto.response;

import com.foodapp.common.enums.Enum;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    String email;
    String username;
    String password;
    List<AddressResponse> address;
    Enum.Role role;
    Enum.UserType userType;
    String mobileNumber;
    String emailId;
}
