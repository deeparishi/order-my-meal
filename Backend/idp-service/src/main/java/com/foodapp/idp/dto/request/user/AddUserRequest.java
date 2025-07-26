package com.foodapp.idp.dto.request.user;

import com.foodapp.idp.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    String emailId;
    String username;
    String password;
    List<AddressRequest> address;
    Enum.UserType userType;
    String mobileNumber;
}
