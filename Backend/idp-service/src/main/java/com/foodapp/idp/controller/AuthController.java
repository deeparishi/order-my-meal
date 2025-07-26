package com.foodapp.idp.controller;

import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.dto.GenericResponse;
import com.foodapp.idp.dto.request.auth.LoginRequest;
import com.foodapp.idp.dto.request.user.AddUserRequest;
import com.foodapp.idp.dto.response.auth.LoginResponse;
import com.foodapp.idp.service.auth.AuthService;
import com.foodapp.idp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public GenericResponse<UserResponse> addUser(@RequestBody AddUserRequest userRequest) {
        UserResponse response = userService.addUser(userRequest);
        return GenericResponse.success(200, AppMessages.USER_ADDED_MESSAGE, response);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(
                GenericResponse.success(200, AppMessages.USER_AUTHENTICATED_MSG, response)
        );
    }

    @GetMapping("/authenticate-token")
    public ResponseEntity<GenericResponse<Map<String, String>>> authenticateUser(@RequestParam("token") String token,
                                                                    @RequestParam(value = "refresh-token", required = false) String refreshToken) {
        Map<String, String> response = authService.authenticateUser(token, refreshToken);
        return ResponseEntity.ok(
                GenericResponse.success(200, AppMessages.TOKEN_VALIDATED, response)
        );
    }
}
