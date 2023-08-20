package com.ozeryavuzaslan.departmentservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomMessageHandler {
    private final MessageSource messageSource;

    public String returnProperMessage(String requestedMsg, String defaultMsg){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(requestedMsg, null, defaultMsg, locale);
    }
}