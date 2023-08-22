package com.ozeryavuzaslan.util;

import com.ozeryavuzaslan.listener.Listener;
import com.ozeryavuzaslan.service.ProducerService;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    public Logger getSpecificLogger(CustomLoggerClassType customLoggerClassType){
        return switch (customLoggerClassType) {
            case USER_PRODUCER -> LoggerFactory.getLogger(ProducerService.class);
            case USER_CONSUMER -> LoggerFactory.getLogger(Listener.class);
        };
    }
}