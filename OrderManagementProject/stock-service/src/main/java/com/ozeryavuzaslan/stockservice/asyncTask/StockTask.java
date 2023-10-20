package com.ozeryavuzaslan.stockservice.asyncTask;

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
    private final StockService stockService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.stock.email.queue.name}")
    private String emailServiceQueueName;

    @Override
    public void run() {
        stockService.checkStockServiceCacheState();
        List<StockDTO> stockDTOList = stockService.getStockList()
                .stream()
                .filter(stockDTO -> stockDTO.getQuantity() == 0)
                .toList();

        if (!stockDTOList.isEmpty()) {
            stockDTOList.forEach(stock -> rabbitTemplate.convertAndSend(emailServiceQueueName, stock));
            System.err.println("STOCK LIST --> " + stockDTOList);
        }
    }
}