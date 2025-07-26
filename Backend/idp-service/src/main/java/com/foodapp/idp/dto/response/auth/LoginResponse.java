package com.foodapp.idp.dto.response.auth;

import com.foodapp.common.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponse {

    UserResponse userResponse;
    String token;
    String refreshToken;

}
