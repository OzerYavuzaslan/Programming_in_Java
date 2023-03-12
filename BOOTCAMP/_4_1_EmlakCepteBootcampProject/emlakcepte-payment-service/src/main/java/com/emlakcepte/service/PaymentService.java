package com.emlakcepte.service;


import com.emlakcepte.configuration.RabbitMQConfiguration;
import com.emlakcepte.model.ConsoleLog;
import com.emlakcepte.model.DbLog;
import com.emlakcepte.model.Log;
import com.emlakcepte.model.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfiguration rabbitMQConfiguration;


    public PaymentService(RabbitTemplate rabbitTemplate, RabbitMQConfiguration rabbitMQConfiguration) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfiguration = rabbitMQConfiguration;

    }

    public void processPayment(Payment payment, String cardNo, Integer extensionAmount){
        Log log = new ConsoleLog("[Emlakcepte-Payment-Service] -> Payment: User ID: "+payment.getUserId()+" - "+ extensionAmount +" month purchased successfully.", LocalDateTime.now());
        Log log1 = new DbLog("[Emlakcepte-Payment-Service] -> Payment: User ID: "+payment.getUserId()+" - "+ extensionAmount +" month purchased successfully.", LocalDateTime.now());
        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(),log);
        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getLogQueueName(),log1);

        for (int i = 0; i < extensionAmount; i++) {

             rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), payment);
        }
    }

}
