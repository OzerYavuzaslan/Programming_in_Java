package com.ozeryavuzaslan.stockservice.kafka;

import com.ozeryavuzaslan.stockservice.dto.OrderEventDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockConsumer.class);

    @KafkaListener(topics = "${kafka.order.topic}", groupId = "${kafka.order.group}", containerFactory = "orderListenerFactory")
    public void consume(ConsumerRecord<String, OrderEventDTO> orderPayload){
        LOGGER.info(String.format("Order event has been received by stock service --> %s", orderPayload.value()));

        //TODO: DB ekle ve orderı save et.


    }
}
