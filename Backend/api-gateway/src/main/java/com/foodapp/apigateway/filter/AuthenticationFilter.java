package com.foodapp.apigateway.filter;

import com.foodapp.apigateway.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;

    @Autowired
    GlobalErrorFilter globalErrorFilter;


    public AuthenticationFilter() {
        super(Config.class);
    }

    @Value("${idp.service.base-url}")
    String identityServiceUrl;

    @Value("${idp.service.authenticate-url}")
    String authenticateURL;


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println(exchange.getRequest());
            boolean flag = validator.isSecured.test(exchange.getRequest());
            if (flag) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return globalErrorFilter.handleError(exchange, "Token is missing", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = Objects.requireNonNull(
                        exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)
                ).getFirst();

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    String urlWithParams = identityServiceUrl + authenticateURL + "?token=" + authHeader;
                    template.getForObject(urlWithParams, String.class);

                } catch (Exception e) {
                    return globalErrorFilter.handleError(exchange, "Authentication failed, please login again", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        });
    }


    public static class Config {

    }
}