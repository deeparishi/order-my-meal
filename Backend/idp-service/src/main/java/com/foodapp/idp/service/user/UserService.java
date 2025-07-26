package com.foodapp.idp.service.user;

import com.foodapp.common.dto.response.AddressResponse;
import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.dto.request.user.AddUserRequest;
import com.foodapp.idp.dto.request.user.AddressRequest;
import com.foodapp.idp.exception.NotFound;
import com.foodapp.idp.mapper.UserMapper;
import com.foodapp.idp.model.Address;
import com.foodapp.idp.model.User;
import com.foodapp.idp.repo.UserRepo;
import com.foodapp.idp.security.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTService jwtService;

    public UserResponse addUser(AddUserRequest userRequest) {

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        if (user.getAddress() != null)
            user.getAddress().forEach(address -> address.setUser(user));

        userRepo.save(user);
        return userMapper.toResponse(user);
    }

    public AddressResponse addAddressToUser(int userId, AddressRequest addressRequest) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFound(AppMessages.USER_NOT_FOUND));

        Address address = userMapper.toEntity(addressRequest);
        address.setUser(user);

        if (user.getAddress() == null)
            user.setAddress(new ArrayList<>());

        user.getAddress().add(address);
        userRepo.save(user);

        return userMapper.toResponse(address);
    }

    public List<UserResponse> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserResponse> responses = users.stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
        return responses;
    }

    public UserResponse getUserByToken(String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmailOrMobileNumber(email)
                .orElseThrow(() -> new NotFound(AppMessages.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(int userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFound(AppMessages.USER_NOT_FOUND));

        return userMapper.toResponse(user);
    }
}
