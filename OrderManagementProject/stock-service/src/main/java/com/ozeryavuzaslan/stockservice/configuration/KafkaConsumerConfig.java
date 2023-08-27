package com.ozeryavuzaslan.stockservice.configuration;

import com.ozeryavuzaslan.basedomains.dto.OrderEventDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("{kafka.order.group}")
    private String orderGroup;

    @Value("${bootstrap.server}")
    private String bootstrapServer;

    @Value("${kafka.consumer.offset}")
    private String autoOffsetResetConfig;

    @Bean
    public ConsumerFactory<String, OrderEventDTO> userConsumerFactory(){
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.GROUP_ID_CONFIG, orderGroup);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.TYPE_MAPPINGS, "OrderEventDTO : com.ozeryavuzaslan.basedomains.dto.OrderEventDTO");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEventDTO> orderListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, OrderEventDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());

        return factory;
    }
}