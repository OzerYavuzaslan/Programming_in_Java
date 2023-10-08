package com.ozeryavuzaslan.orderservice.kafka;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final NewTopic topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${kafka.approved.order.topic}")
    private String orderTopic;

    public void sendMessage(OrderDTO orderDTO){
        kafkaTemplate.send(orderTopic, orderDTO);
        LOGGER.info(String.format("Order event has been sent --> %s", orderDTO));
    }
}
