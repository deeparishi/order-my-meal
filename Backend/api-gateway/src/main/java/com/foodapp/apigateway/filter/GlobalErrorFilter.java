package com.foodapp.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.apigateway.exception.UnAuthorizedException;
import com.foodapp.common.dto.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalErrorFilter implements GlobalFilter, Ordered {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(UnAuthorizedException.class, ex -> handleError(exchange, ex.getMessage(), HttpStatus.UNAUTHORIZED))
                .onErrorResume(Exception.class, ex -> {
                    if (ex.getMessage() != null && ex.getMessage().contains("fallback")) {
                        return Mono.error(ex);
                    }
                    return handleError(exchange, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    public Mono<Void> handleError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        GenericResponse<String> errorResponse = GenericResponse.error(
                httpStatus.value(),
                httpStatus.name(),
                errorMessage
        );

        try {
            String body = objectMapper.writeValueAsString(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            DataBuffer buffer = response.bufferFactory().wrap(errorMessage.getBytes());
            return response.writeWith(Mono.just(buffer));
        }
    }

    @Override
    public int getOrder() {
        return -10;
    }
}