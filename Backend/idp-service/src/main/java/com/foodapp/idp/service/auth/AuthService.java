package com.foodapp.idp.service.auth;

import com.foodapp.common.dto.response.UserResponse;
import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.dto.request.auth.LoginRequest;
import com.foodapp.idp.dto.response.auth.LoginResponse;
import com.foodapp.idp.exception.TokenExpiredException;
import com.foodapp.idp.mapper.UserMapper;
import com.foodapp.idp.model.User;
import com.foodapp.idp.security.UserDetailService;
import com.foodapp.idp.security.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userDetailService.loadUserByUsername(loginRequest.getEmailOrMobileNumber());
        try {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrMobileNumber(), loginRequest.getPassword(), user.getAuthorities());

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated())
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect Password");
        }
        String token = jwtService.generateAccessToken(user.getEmailId(), user.getAuthorities());
        String refreshToken = jwtService.generateRefreshToken(user.getEmailId());
        UserResponse userResponse = userMapper.toResponse(user);
        return new LoginResponse(userResponse, token, refreshToken);
    }

    public Map<String, String> authenticateUser(String token, String refreshToken) {

        if (jwtService.validateToken(token)) {
            return Map.of("message", AppMessages.TOKEN_VALIDATED.concat("and").concat(AppMessages.USER_AUTHENTICATED_MSG));
        }

        if (jwtService.isTokenExpired(token) && !jwtService.isTokenExpired(refreshToken)) {
            String email = jwtService.extractUsername(token);
            User user = userDetailService.loadUserByUsername(email);
            String newAccessToken = jwtService.generateAccessToken(user.getEmailId(), user.getAuthorities());
            return Map.of("message", AppMessages.ACCESS_TOKEN_EXPIRED,
                    "accessToken", newAccessToken);
        }

        throw new TokenExpiredException(AppMessages.INVALID_TOKEN);
    }
}
