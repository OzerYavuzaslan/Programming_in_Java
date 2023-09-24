package com.ozeryavuzaslan.emailservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@RequiredArgsConstructor
public class RabbitMQListenerConfig implements RabbitListenerConfigurer {
    private final ObjectMapper objectMapper;

    @Value("${rabbit.stock.email.queue.name}")
    private String stockQueueName;

    @Value("${rabbit.payment.email.queue.name}")
    private String paymentQueueName;

    @Value("${rabbit.refund.email.queue.name}")
    private String refundQueueName;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper.findAndRegisterModules());
    }

    @Bean
    public Queue getStockQueue() {
        return new Queue(stockQueueName,false);
    }

    @Bean
    public Queue getPaymentQueue() {
        return new Queue(paymentQueueName,false);
    }

    @Bean
    public Queue getRefundQueue() {
        return new Queue(refundQueueName,false);
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        defaultMessageHandlerMethodFactory.setMessageConverter(jackson2Converter());
        return defaultMessageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }
}
