package com.example.kafkaproducerex.controller;

import com.example.kafkaproducerex.model.Message;
import com.example.kafkaproducerex.service.KafkaSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.kafkaproducerex.config.KafkaTopicConfig.TOPIC_1;
import static com.example.kafkaproducerex.config.KafkaTopicConfig.TOPIC_2;

@RestController
@RequestMapping("/kafka")
public class KafkaSenderController {

    private final KafkaSenderService kafkaSenderService;

    public KafkaSenderController(KafkaSenderService kafkaSenderService) {
        this.kafkaSenderService = kafkaSenderService;
    }

    @PutMapping("/topic1")
    public ResponseEntity<String> putMessageTopic1(@RequestBody Message message){
        kafkaSenderService.sendMessageWithCallback(TOPIC_1, message.getMessage());
        return ResponseEntity.ok(message.getMessage());
    }

    @PutMapping("/topic2")
    public ResponseEntity<String> putMessageTopic2(@RequestBody Message message){
        kafkaSenderService.sendMessageWithCallback(TOPIC_2, message.getMessage());
        return ResponseEntity.ok(message.getMessage());
    }
}
