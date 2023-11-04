package com.ozeryavuzaslan.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/supports")
public class FallbackController {
    public Mono<String> contactSupport() {
        return Mono.just("An error occurred. Please try after some time or contact support team!");
    }
}
