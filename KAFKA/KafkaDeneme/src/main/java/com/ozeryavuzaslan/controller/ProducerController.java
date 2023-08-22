package com.ozeryavuzaslan.controller;

import com.ozeryavuzaslan.service.ProducerService;
import com.ozeryavuzaslan.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("producer")
public class ProducerController {
    private final ProducerService producerService;

    @PostMapping
    public User sendProductMessage(@RequestBody User message){
        producerService.send(message);
        return message;
    }
}