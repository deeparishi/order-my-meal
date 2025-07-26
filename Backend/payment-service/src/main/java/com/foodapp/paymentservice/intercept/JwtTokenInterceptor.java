package com.foodapp.paymentservice.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
public class JwtTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String token = extractTokenFromRequest();

        if (token != null) {
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            log.debug("JWT token added to Feign request");
        } else {
            log.warn("No JWT token found in current request");
        }
    }

    private String extractTokenFromRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    return authHeader.substring(7);
                }
            }
        } catch (Exception e) {
            log.error("Error extracting token from request: {}", e.getMessage());
        }
        return null;
    }
}
