package com.ozeryavuzaslan.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @Value("${service.not.available.exception}")
    private String serviceNotAvailableMsg;

    @RequestMapping("/orders")
    public ResponseEntity<String> ordersFallback() {
        return new ResponseEntity<>("Order " + serviceNotAvailableMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/stocks")
    public ResponseEntity<String> stocksFallback() {
        return new ResponseEntity<>("Stock " + serviceNotAvailableMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/revenues")
    public ResponseEntity<String> revenuesFallback() {
        return new ResponseEntity<>("Revenue " + serviceNotAvailableMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/payments")
    public ResponseEntity<String> paymentsFallback() {
        return new ResponseEntity<>("Payment " + serviceNotAvailableMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }
}