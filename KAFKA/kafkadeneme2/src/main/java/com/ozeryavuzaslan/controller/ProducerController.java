package com.ozeryavuzaslan.controller;

import com.ozeryavuzaslan.model.Human;
import com.ozeryavuzaslan.model.User;
import com.ozeryavuzaslan.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("producer")
public class ProducerController {
    private final ProducerService producerService;

    @PostMapping
    public List<User> sendUserMessage(@RequestBody List<User> message){
        producerService.sendMessage(message);
        return message;
    }

    @PostMapping("/human")
    public List<Human> sendHumanMessage(@RequestBody List<Human> message){
        producerService.sendHumanMessage(message);
        return message;
    }
}