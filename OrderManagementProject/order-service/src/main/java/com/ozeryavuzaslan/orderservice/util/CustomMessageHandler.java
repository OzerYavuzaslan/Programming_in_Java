package com.ozeryavuzaslan.orderservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMessageHandler {
    private final MessageSource messageSource;

    public String returnProperMessage(String requestedMsg, String defaultMsg){
        return messageSource.getMessage(requestedMsg, null, defaultMsg, LocaleContextHolder.getLocale());
    }
}