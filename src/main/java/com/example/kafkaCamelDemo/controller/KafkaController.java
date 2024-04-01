package com.example.kafkaCamelDemo.controller;

import com.example.kafkaCamelDemo.service.KafkaConsumerService;
import com.example.kafkaCamelDemo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private KafkaConsumerService consumerService;

    List<String> topics = Arrays.asList("topic1", "topic2", "topic3");

    @PostMapping("/sendJson")
    public String sendJsonMessage(@RequestBody String jsonMessage) {
        producerService.sendMessage(topics, jsonMessage);
        return "JSON Message sent successfully!";
    }

//    @PostMapping("/sendList")
//    public String sendListMessage(@RequestBody List<String> listMessage) {
//        String message = String.join(",", listMessage);
//        producerService.sendMessage(message);
//        return "List Message sent successfully!";
//    }
//
//    @PostMapping("/sendArray")
//    public String sendArrayMessage(@RequestBody String[] arrayMessage) {
//        String message = String.join(",", Arrays.asList(arrayMessage));
//        producerService.sendMessage(message);
//        return "Array Message sent successfully!";
//    }
//    @GetMapping("/send")
//    public String sendMessage() {
//        producerService.sendMessage("Hello Kafka!");
//        return "Message sent successfully!";
//    }

    @GetMapping("/consume")
    public List<String> consumeMessages() {
        return consumerService.getConsumedMessages();
    }
}
