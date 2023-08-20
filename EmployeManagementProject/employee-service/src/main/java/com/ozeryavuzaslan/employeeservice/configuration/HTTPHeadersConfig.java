package com.ozeryavuzaslan.employeeservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class HTTPHeadersConfig {
    @Bean
    HttpHeaders getHeaderInstance(){
        return new HttpHeaders();
    }
}