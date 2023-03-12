package emlakcepte.configuration;

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

    private String queueName = "emlakcepte.notification";
    private String paymentQueueName = "emlakcepte.payment";

    @Value("${emlakcepte.rabbitmq.realtyQueue}")
    private String realtyQueueName;


    @Value("${emlakcepte.rabbitmq.logQueue}")
    private String logQueueName;

    private String logExchange = "emlakcepte.log-exchange";

    private String exchange = "emlakcepte.notification-exchange";

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueueName, false);
    }

    @Bean
    public Queue realtyQueue() {
        return new Queue(realtyQueueName, false);
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
    public DirectExchange logExchange() {
        return new DirectExchange(logExchange);
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
        Jackson2JsonMessageConverter jackson2JsonMessageConverter
                = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;
        //return new Jackson2JsonMessageConverter();
    }

    public String getQueueName() {
        return queueName;
    }


    public String getpaymentQueueName() {
        return paymentQueueName;
    }

    public String getrealtyQueueName() {
        return realtyQueueName;
    }

    public String getLogQueueName() {
        return logQueueName;
    }

    public void setLogQueueName(String logQueueName) {
        this.logQueueName = logQueueName;
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
