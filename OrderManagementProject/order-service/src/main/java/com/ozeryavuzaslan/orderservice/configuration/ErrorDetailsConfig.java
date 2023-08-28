package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    @Bean
    public ErrorDetailsDTO getErrorDetail(){
        return new ErrorDetailsDTO();
    }
}