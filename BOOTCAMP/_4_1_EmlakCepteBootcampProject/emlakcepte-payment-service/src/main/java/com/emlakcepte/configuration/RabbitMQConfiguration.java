package com.emlakcepte.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@Configuration
public class RabbitMQConfiguration {

    @Value("${emlakcepte.rabbitmq.queue}")
    private String queueName;

    private String exchange = "emlakcepte.payment-exchange";
    private String logExchange = "emlakcepte.log-exchange";

    private String routingKey="emlakcepte.payment-routing";

    private String logRoutingKey="emlakcepte.log-routing";


    @Value("${emlakcepte.rabbitmq.logQueue}")
    private String logQueueName;

    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange(logExchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(logQueueName, false);
    }


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }


    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding logBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(logRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter
                = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;
        //return new Jackson2JsonMessageConverter();
    }

    /*@Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }*/

    /*@Bean
    public MessageConverter jsonMessageConverter2() {
        return new SerializerMessageConverter();
    }
*/
    public String getQueueName() {
        return queueName;
    }

    public String getLogQueueName() {
        return logQueueName;
    }


    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
