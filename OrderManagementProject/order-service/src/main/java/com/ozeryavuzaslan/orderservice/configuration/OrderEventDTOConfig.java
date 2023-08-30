package com.ozeryavuzaslan.orderservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.OrderEventDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderEventDTOConfig {
    @Bean
    public OrderEventDTO getOrderEventDTOBean(){
        return new OrderEventDTO();
    }
}
