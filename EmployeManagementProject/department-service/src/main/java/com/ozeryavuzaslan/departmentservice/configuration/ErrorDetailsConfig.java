package com.ozeryavuzaslan.departmentservice.configuration;

import com.ozeryavuzaslan.departmentservice.dto.response.ErrorDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDetailsConfig {
    private static ErrorDetails errorDetails;

    @Bean
    public ErrorDetails getErrorDetail(){
        if (errorDetails == null)
            errorDetails = new ErrorDetails();

        return errorDetails;
    }
}
