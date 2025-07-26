package com.foodapp.restaurantservice.intercept;

import com.foodapp.restaurantservice.feign.ApiGatewayFeign;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class CorrelationIdFilter extends OncePerRequestFilter {

    @Autowired
    ApiGatewayFeign apiGatewayFeign;

    public static String CORRELATION_ID = "NOT_ASSIGNED";

    @PostConstruct
    public void getCorrelationId() {
        CORRELATION_ID = apiGatewayFeign.getCorrelationId();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String id = request.getHeader(CORRELATION_ID);
        String name = request.getRequestURI();

        log.info("food-app-correlation-id found in RequestTraceFilter : {}", id);
        log.info("food-app-correlation-id found in RequestTraceFilter URI {}: ", name);
        filterChain.doFilter(request, response);
    }
}
