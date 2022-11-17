package com.example.kafkaproducerex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaSenderService {

    private final Logger LOG = LoggerFactory.getLogger(KafkaSenderService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSenderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String message){
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessageWithCallback(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Message [{}] delivered with offset: {}, partition: {}, topic: {}",
                        message,
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().topic()
                );
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Unable to deliver message [{}]. {}",
                        message,
                        ex.getMessage());
            }
        });
    }
}
