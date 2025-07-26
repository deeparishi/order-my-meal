package com.foodapp.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class RouteValidator {


    public static final List<String> WHITELISTED_ENDPOINTS =
            List.of(
                    "/idp/v1/auth/login",
                    "/idp/v1/auth/register",
                    "/idp/v1/auth/authenticate-token"
            );

    public Predicate<ServerHttpRequest> isSecured =
            request -> WHITELISTED_ENDPOINTS.stream()
                    .noneMatch(uri -> {
                        log.info("Request URI {} ", request.getURI());
                        return request.getURI().getPath().contains(uri);
                    });

}
