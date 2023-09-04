package com.ozeryavuzaslan.emailservice.rabbitmq;

import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.emailservice.util.email.EmailServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListener {
    private final EmailServiceUtil emailServiceUtil;

    @RabbitListener(queues = "${rabbit.stock.email.queue.name}")
    public void paymentListener(StockDTO stockDTO) {
        System.err.println("STOCK: " + stockDTO);
    }
}
