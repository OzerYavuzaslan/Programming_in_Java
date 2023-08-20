package com.ozeryavuzaslan.util;

import com.ozeryavuzaslan.service.WikimediaRecentChangeProducerService;
import com.ozeryavuzaslan.util.enums.CustomLoggerClassType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    public Logger getSpecificLogger(CustomLoggerClassType customLoggerClassType){
        return switch (customLoggerClassType) {
            case PRODUCER_SERVICE -> LoggerFactory.getLogger(WikimediaRecentChangeProducerService.class);
            case WIKIMEDIA_CHANGES_HANDLER -> LoggerFactory.getLogger(WikimediaChangesHandler.class);
        };
    }
}