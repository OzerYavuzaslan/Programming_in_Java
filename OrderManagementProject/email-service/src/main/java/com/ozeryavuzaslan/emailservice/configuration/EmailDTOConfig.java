package com.ozeryavuzaslan.emailservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailStatus;
import com.ozeryavuzaslan.basedomains.dto.enums.EmailType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailDTOConfig {
    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.from}")
    private String mailFrom;

    @Bean
    private EmailDTO getBaseEmailDTO(EmailType emailType){
        return EmailDTO
                .builder()
                .mailFrom(mailFrom)
                .username(username)
                .password(password)
                .emailStatus(EmailStatus.NOT_SENT)
                .build();
    }
}