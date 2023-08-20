package com.ozeryavuzaslan.employeeservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/users")
public class MessageController {
    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("/message")
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok(message);
    }
}
