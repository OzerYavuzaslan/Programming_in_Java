package com.ozeryavuzaslan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.user.topic}")
    private String topic;

    public void send(Object message){
        this.kafkaTemplate.send(topic, message);
        System.err.println("Message is sent to kafka : " + message);
    }
}
