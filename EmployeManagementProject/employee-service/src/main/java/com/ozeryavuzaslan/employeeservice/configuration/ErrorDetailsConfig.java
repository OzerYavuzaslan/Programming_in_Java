package com.ozeryavuzaslan.employeeservice.configuration;

import com.ozeryavuzaslan.employeeservice.dto.response.ErrorDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    @Bean
    public ErrorDetails getErrorDetail(){
        return new ErrorDetails();
    }
}