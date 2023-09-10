package com.ozeryavuzaslan.paymentservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    @Bean
    public ErrorDetailsDTO getErrorDetailBean(){
        return new ErrorDetailsDTO();
    }
}