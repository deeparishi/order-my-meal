package com.foodapp.idp.config;

import com.foodapp.idp.enums.Enum;
import com.foodapp.idp.repo.UserRepo;
import com.foodapp.idp.security.UserDetailService;
import com.foodapp.idp.security.entrypoint.CustomAuthenticationEntryPoint;
import com.foodapp.idp.security.entrypoint.RestAuthenticationEntryPoint;
import com.foodapp.idp.security.handler.CustomAccessDeniedHandler;
import com.foodapp.idp.security.jwt.JWTRequestFilter;
import com.foodapp.idp.security.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Autowired
    JWTRequestFilter jwtRequestFilter;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    private final List<String> WHITELISTED_ENDPOINTS = List.of(
            "v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/error",
            "/actuator/prometheus");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( req ->
                        req.requestMatchers(WHITELISTED_ENDPOINTS.toArray(String[]::new))
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .userDetailsService(userDetailService)
                .sessionManagement(session ->  session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
