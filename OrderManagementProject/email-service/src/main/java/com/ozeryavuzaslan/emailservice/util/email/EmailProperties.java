package com.ozeryavuzaslan.emailservice.util.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailProperties {
    private final Properties properties;
    public Properties getEmailProperties(){
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "587"); //465

        return properties;
    }
}