package com.ozeryavuzaslan.stockservice.configuration;

import com.ozeryavuzaslan.stockservice.util.StockTask;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@Configuration
@RequiredArgsConstructor
public class StockTaskConfig{
    private final StockTask stockTask;

    @Bean
    public Timer getTimerBean(){
        Timer timer = new Timer();
        timer.schedule(stockTask, 0, 60000);
        return new Timer();
    }
}
