package com.ozeryavuzaslan.stockservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Data
@Configuration
public class RabbitMQConfig {
    @Value("${rabbit.stock.email.queue.name}")
    private String emailServiceQueueName;

    @Value("${rabbit.stock.email.exchange}")
    private String emailServiceExchange;

    @Bean
    public Queue queue() {
        return new Queue(emailServiceQueueName, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(emailServiceExchange);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();

        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
