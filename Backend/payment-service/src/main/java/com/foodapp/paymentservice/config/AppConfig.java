package com.foodapp.paymentservice.config;

import com.foodapp.paymentservice.intercept.JwtTokenInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public RequestInterceptor requestInterceptor() {
        return new JwtTokenInterceptor();
    }
}
