package com.ozeryavuzaslan.service;

import com.ozeryavuzaslan.model.Human;
import com.ozeryavuzaslan.model.User;
import com.ozeryavuzaslan.util.CustomLogger;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CustomLogger customLogger;

    @Value("${kafka.user.topic}")
    private String topic;

    @Value("${kafka.human.topic}")
    private String humanTopic;

    public void sendMessage(List<User> message){
        this.kafkaTemplate.send(topic, message);
        customLogger.getSpecificLogger(CustomLoggerClassType.USER_PRODUCER).info(String.format("Message sent %s", message));
    }

    public void sendHumanMessage(List<Human> message){
        this.kafkaTemplate.send(humanTopic, message);
        customLogger.getSpecificLogger(CustomLoggerClassType.USER_PRODUCER).info(String.format("Message sent %s", message));
    }
}