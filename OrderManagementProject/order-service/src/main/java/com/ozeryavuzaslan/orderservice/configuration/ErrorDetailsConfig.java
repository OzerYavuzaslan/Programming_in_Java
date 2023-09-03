package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.emails.ErrorDetailsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    @Bean
    public ErrorDetailsDTO getErrorDetailBean(){
        return new ErrorDetailsDTO();
    }
}