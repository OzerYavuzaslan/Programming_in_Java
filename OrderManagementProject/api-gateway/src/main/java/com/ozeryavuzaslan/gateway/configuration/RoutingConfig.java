package com.ozeryavuzaslan.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class RoutingConfig {
    @Bean
    public RouteLocator myOrderRouting(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/api/v1/orders/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("orderServiceCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://order-service"))
                .route(p -> p
                        .path("/api/v1/stocks/**")
                        .filters(f -> f.rewritePath("/api/v1/stocks/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker((config -> config.setName("stockServiceCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))))
                        .uri("lb://stock-service"))
                .route(p -> p
                        .path("/api/v1/payments/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker((config -> config.setName("paymentServiceCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))))
                        .uri("lb://payment-service"))
                .route(p -> p
                        .path("/api/v1/revenues/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("revenueServiceCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://revenue-service")).build();
    }
}
