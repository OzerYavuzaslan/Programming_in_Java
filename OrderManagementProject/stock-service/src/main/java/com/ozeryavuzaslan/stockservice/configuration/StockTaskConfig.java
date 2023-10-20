package com.ozeryavuzaslan.stockservice.configuration;

import com.ozeryavuzaslan.stockservice.asyncTask.StockTask;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@Configuration
@RequiredArgsConstructor
public class StockTaskConfig {
    private final StockTask stockTask;
    private Timer timer;

    @PostConstruct
    public void init() {
        timer = new Timer();
        timer.schedule(stockTask, 0, 3000000);
    }

    @Bean
    public Timer getTimerBean() {
        return timer;
    }
}
