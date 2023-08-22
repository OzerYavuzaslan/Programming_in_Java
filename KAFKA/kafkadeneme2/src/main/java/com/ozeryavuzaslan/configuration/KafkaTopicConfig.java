package com.ozeryavuzaslan.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.user.topic}")
    private String userTopic;

    @Value("${kafka.human.topic}")
    private String humanTopic;

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(userTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic humanTopic() {
        return TopicBuilder.name(humanTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }
}
