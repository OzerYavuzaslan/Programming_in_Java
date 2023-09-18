package com.ozeryavuzaslan.emailservice.service.kafka;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderEventDTO;
import com.ozeryavuzaslan.emailservice.util.email.EmailServiceUtil;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {
    private final EmailServiceUtil emailServiceUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "${kafka.order.topic}", groupId = "${kafka.email.group}", containerFactory = "emailListenerFactory")
    public void consume(ConsumerRecord<String, OrderEventDTO> orderPayload){
        LOGGER.info(String.format("Order event has been received by stock service --> %s", orderPayload.value()));
        emailServiceUtil.sendEmail(orderPayload.value());
    }
}
