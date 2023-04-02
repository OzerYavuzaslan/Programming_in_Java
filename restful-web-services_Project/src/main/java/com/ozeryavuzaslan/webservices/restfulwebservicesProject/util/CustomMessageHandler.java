package com.ozeryavuzaslan.webservices.restfulwebservicesProject.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Component
@RequiredArgsConstructor
public class CustomMessageHandler {
    private final MessageSource messageSource;

    public String returnProperMessage(String requestedMsg, String defaultMsg){
        Locale locale = LocaleContextHolder.getLocale();
        return getMessageSource().getMessage(requestedMsg, null, defaultMsg, locale);
    }
}