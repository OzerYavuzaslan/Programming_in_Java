package com.ozeryavuzaslan.listener;

import com.ozeryavuzaslan.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    @KafkaListener(topics = "${kafka.user.topic}", groupId = "${kafka.user.group}", containerFactory = "userListenerFactory")
    public void consume(ConsumerRecord<String, User> payload){
        System.out.println(payload.value());
    }
}