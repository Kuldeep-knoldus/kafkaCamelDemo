package com.example.kafkaCamelDemo.service;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.kafka.support.KafkaHeaders.GROUP_ID;

@Getter
@Service
public class KafkaConsumerService {

    @Value("#{'${kafka.consumer.topics}'.split(',')}")
    private List<String> topics;

    private final List<String> consumedMessages = new ArrayList<>();

    @KafkaListener(id = "myListener", groupId = "my_group_id", topicPattern = "topic.*")
    public void consume(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        String message = record.value();
        System.out.println("Consumed message from topic " + topic + ": " + message);
        consumedMessages.add("Topic: " + topic + ", Message: " + message);
    }
}
