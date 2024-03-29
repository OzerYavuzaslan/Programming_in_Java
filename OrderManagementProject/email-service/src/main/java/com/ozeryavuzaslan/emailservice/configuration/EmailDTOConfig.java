package com.ozeryavuzaslan.emailservice.configuration;

import com.ozeryavuzaslan.emailservice.dto.EmailDTO;
import com.ozeryavuzaslan.emailservice.dto.enums.EmailStatus;
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
    public EmailDTO getEmailDTO(){
        return EmailDTO
                .builder()
                .mailFrom(mailFrom)
                .username(username)
                .password(password)
                .emailStatus(EmailStatus.NOT_SENT)
                .build();
    }
}