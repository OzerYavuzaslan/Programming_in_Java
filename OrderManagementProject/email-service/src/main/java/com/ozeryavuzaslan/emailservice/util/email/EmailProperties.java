package com.ozeryavuzaslan.emailservice.util.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailProperties {
    private final Properties properties;

    @Value("${email.smtp.auth.property.name}")
    private String emailSmtpAuthPropertyName;

    @Value("${email.smtp.auth.property.value}")
    private String emailSmtpAuthPropertyValue;

    @Value("${email.smtp.starttls.enable.property.name}")
    private String emailSmtpStartTtlsEnablePropertyName;

    @Value("${email.smtp.starttls.enable.property.value}")
    private String emailSmtpStartTtlsEnablePropertyValue;

    @Value("${email.smtp.host.property.name}")
    private String emailSmtpHostPropertyName;

    @Value("${email.smtp.host.property.value}")
    private String emailSmtpHostPropertyValue;

    @Value("${email.smtp.port.property.name}")
    private String emailSmtpPortPropertyName;

    @Value("${email.smtp.port.property.value}")
    private String emailSmtpPortPropertyValue;

    public Properties getEmailProperties(){
        properties.put(emailSmtpAuthPropertyName, emailSmtpAuthPropertyValue);
        properties.put(emailSmtpStartTtlsEnablePropertyName, emailSmtpStartTtlsEnablePropertyValue);
        properties.put(emailSmtpHostPropertyName, emailSmtpHostPropertyValue);
        properties.put(emailSmtpPortPropertyName, emailSmtpPortPropertyValue); //465

        return properties;
    }
}