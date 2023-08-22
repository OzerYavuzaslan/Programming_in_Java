package com.ozeryavuzaslan.listener;

import com.ozeryavuzaslan.model.Human;
import com.ozeryavuzaslan.model.User;
import com.ozeryavuzaslan.util.CustomLogger;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Listener {
    private final CustomLogger customLogger;

    @KafkaListener(topics = "${kafka.user.topic}", groupId = "${kafka.user.group}", containerFactory = "userListenerFactory")
    void userListener(List<User> userData) {
        for (User singleUserObject : userData)
            customLogger
                    .getSpecificLogger(CustomLoggerClassType.USER_CONSUMER)
                    .info(String.format("Message contains %s", singleUserObject));
    }

    @KafkaListener(topics = "${kafka.human.topic}", groupId = "${kafka.human.group}", containerFactory = "humanListenerFactory")
    void humanListener(List<Human> humanData) {
        for (Human singleHumanObject : humanData)
            customLogger
                    .getSpecificLogger(CustomLoggerClassType.USER_CONSUMER)
                    .info(String.format("Message contains %s", singleHumanObject.getFullName()));
    }
}