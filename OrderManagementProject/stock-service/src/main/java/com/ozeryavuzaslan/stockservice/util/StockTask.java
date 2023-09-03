package com.ozeryavuzaslan.stockservice.util;

import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class StockTask extends TimerTask {
    private final RabbitTemplate rabbitTemplate;
    private final StockRepository stockRepository;

    @Value("${rabbit.stock.email.queue.name}")
    private String emailServiceQueueName;

    @Override
    public void run() {
        List<Stock> stockList = stockRepository
                .findAll()
                .stream()
                .filter(stock -> stock.getQuantity() == 0)
                .toList();

        stockList.forEach(stock -> rabbitTemplate.convertAndSend(emailServiceQueueName, stock));
    }
}