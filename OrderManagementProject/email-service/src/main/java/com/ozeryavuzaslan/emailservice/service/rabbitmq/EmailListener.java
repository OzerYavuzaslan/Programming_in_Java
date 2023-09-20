package com.ozeryavuzaslan.emailservice.service.rabbitmq;

import com.ozeryavuzaslan.basedomains.dto.emails.EmailDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.emailservice.util.email.EmailServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
@RequiredArgsConstructor
public class EmailListener {
    private final EmailDTO emailDTO;
    private final EmailServiceUtil emailServiceUtil;

    @RabbitListener(queues = "${rabbit.stock.email.queue.name}")
    public void paymentListener(StockDTO stockDTO) {
        //TODO: emailDTO içini stockDTO ile doldur
        emailServiceUtil.sendEmail(emailDTO);
        System.err.println("STOCK: " + stockDTO);
    }
}
