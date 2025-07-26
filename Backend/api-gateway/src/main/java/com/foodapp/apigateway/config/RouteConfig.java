package com.foodapp.apigateway.config;

import com.foodapp.apigateway.exception.UnAuthorizedException;
import com.foodapp.apigateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static com.foodapp.apigateway.utils.AppConstants.*;

@Configuration
public class RouteConfig {

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator foodAppRouteSetup(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/gateway/idp/**")
                        .filters(f -> f
                                .rewritePath("/gateway/idp/(?<segment>.*)", "/idp/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("idpCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI)
                                )
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri(LB_IDP_SERVICE)
                )
                .route(p -> p
                        .path("/gateway/restaurant/**")
                        .filters(f -> f
                                .rewritePath("/gateway/restaurant/(?<segment>.*)", "/restaurant/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("restaurantCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI))
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri(LB_RESTAURANT_SERVICE)
                )
                .route(p -> p
                        .path("/gateway/payment/**")
                        .filters(f -> f
                                .rewritePath("/gateway/payment/(?<segment>.*)", "/payment/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("paymentCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI))
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri(LB_PAYMENT_SERVICE)
                )
                .route(p -> p
                        .path("/gateway/order/**")
                        .filters(f -> f
                                .rewritePath("/gateway/order/(?<segment>.*)", "/order/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("orderCircuitBreaker")
                                        .setFallbackUri(FALLBACK_URI))
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri(LB_ORDER_SERVICE)
                )
                .build();
    }
}
