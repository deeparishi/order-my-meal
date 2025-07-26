package com.foodapp.idp.controller;

import com.foodapp.common.dto.response.AddressResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.idp.dto.GenericResponse;
import com.foodapp.idp.dto.request.user.AddUserRequest;
import com.foodapp.idp.dto.request.user.AddressRequest;
import com.foodapp.idp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity<GenericResponse<UserResponse>> getUserById(@PathVariable int userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(
                GenericResponse.success(
                        200,
                        "User fetched successfully!",
                        response)
        );
    }

    @GetMapping("/fetch-all-users")
    public ResponseEntity<GenericResponse<List<UserResponse>>> getAllUser() {
        List<UserResponse> responses = userService.getAllUser();
        return ResponseEntity.ok(
                GenericResponse.success(200, "All users fetched successfully!", Collections.singletonList(responses))
        );
    }


    @PostMapping("/{id}/add-address-to-user")
    public GenericResponse<AddressResponse> addAddressToUser(@PathVariable int id,
                                                             @RequestBody AddressRequest addressRequest) {
        AddressResponse response = userService.addAddressToUser(id, addressRequest);
        return GenericResponse.success(200, "Address Added Successfully!", response);

    }

    @GetMapping("/user-details-with-token/{token}")
    public ResponseEntity<GenericResponse<UserResponse>> getUserByToken(@PathVariable String token) {
        UserResponse response = userService.getUserByToken(token);
        return ResponseEntity.ok(
                GenericResponse.success(200, "All users fetched successfully!", response)
        );
    }

}
