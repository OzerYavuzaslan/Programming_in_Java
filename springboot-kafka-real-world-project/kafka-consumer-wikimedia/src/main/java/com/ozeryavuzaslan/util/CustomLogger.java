package com.ozeryavuzaslan.util;

import com.ozeryavuzaslan.service.WikimediaRecentChangeConsumerService;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    public Logger getSpecificLogger(CustomLoggerClassType customLoggerClassType){
        return switch (customLoggerClassType) {
            case CONSUMER_SERVICE -> LoggerFactory.getLogger(WikimediaRecentChangeConsumerService.class);
        };
    }
}