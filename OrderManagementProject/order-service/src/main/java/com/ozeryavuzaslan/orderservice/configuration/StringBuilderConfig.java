package com.ozeryavuzaslan.orderservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringBuilderConfig {
    @Bean
    public StringBuilder getStringBuilderBean(){
        return new StringBuilder();
    }
}
