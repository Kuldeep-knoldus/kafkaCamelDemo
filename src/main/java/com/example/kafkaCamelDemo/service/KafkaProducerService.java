package com.example.kafkaCamelDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "my_topic";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    public void sendMessage(Object message) {
//        kafkaTemplate.send(TOPIC, message);
//    }

    public void sendMessage(List<String> topics, Object message) {
        for (String topic : topics) {
            kafkaTemplate.send(topic, message);
        }
    }
}
