package com.ozeryavuzaslan.orderservice.kafka;

import com.ozeryavuzaslan.basedomains.dto.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.OrderEventDTO;
import com.ozeryavuzaslan.orderservice.converter.OrderConvert;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final NewTopic topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderConvert orderConvert;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public void sendMessage(OrderDTO orderDTO){
        OrderEventDTO orderEventDTO = orderConvert.convert(orderDTO);

        Message<OrderEventDTO> orderEventDTOMessage = MessageBuilder
                .withPayload(orderEventDTO)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(orderEventDTOMessage);

        LOGGER.info(String.format("Order event has been sent --> %s", orderEventDTO));
    }
}
