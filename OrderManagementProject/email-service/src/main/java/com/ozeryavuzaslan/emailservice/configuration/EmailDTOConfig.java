package com.ozeryavuzaslan.emailservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.EmailDTO;
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
    private String emailFrom;

    @Bean
    public EmailDTO getEmailDTO(){
        return new EmailDTO(username, password, emailFrom, "Subject", "Body");
    }
}