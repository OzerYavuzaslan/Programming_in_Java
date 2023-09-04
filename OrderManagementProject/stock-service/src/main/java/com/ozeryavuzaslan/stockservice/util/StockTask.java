package com.ozeryavuzaslan.stockservice.util;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.stockservice.service.StockService;
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
    private final StockService stockService;

    @Value("${rabbit.stock.email.queue.name}")
    private String emailServiceQueueName;

    @Override
    public void run() {
        List<StockDTO> stockDTOList = stockService.getStockList()
                .stream()
                .filter(stockDTO -> stockDTO.getQuantity() == 0)
                .toList();

        if (!stockDTOList.isEmpty()) {
            stockDTOList.forEach(stock -> rabbitTemplate.convertAndSend(emailServiceQueueName, stockDTOList));
            System.err.println("GONDERILDI!");
        }
    }
}