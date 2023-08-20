package com.ozeryavuzaslan.organizationservice.configuration;

import com.ozeryavuzaslan.organizationservice.dto.response.ErrorDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    @Bean
    public ErrorDetails getErrorDetail(){
        return new ErrorDetails();
    }
}