package com.ozeryavuzaslan.orderservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapperBean(){
        return new ModelMapper();
    }
}
