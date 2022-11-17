package com.example.kafkaproducerex.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    public static final String TOPIC_1 = "dh-topic-1";
    public static final String TOPIC_2 = "dh-topic-2";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics topics12() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(TOPIC_1)
                        .build(),
                TopicBuilder.name(TOPIC_2)
                        .partitions(6)
                        .replicas(1)
                        .build());
    }
}
