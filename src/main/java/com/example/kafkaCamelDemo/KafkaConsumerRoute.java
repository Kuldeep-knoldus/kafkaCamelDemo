package com.example.kafkaCamelDemo;

import com.example.kafkaCamelDemo.service.KafkaConsumerService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRoute extends RouteBuilder {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

//    @Override
//    public void configure() throws Exception {
////        from("kafka:my_topic?brokers=localhost:9092")
//        from("kafka:my_topic")
//                .routeId("KafkaConsumerRoute")
//                .unmarshal().json()
//                .process(exchange -> {
//                    String id = exchange.getIn().getBody(String.class);
//                    consumerService.consume(id);
//                });
//    }

    @Override
    public void configure() throws Exception {
        from("kafka:topic1?brokers=localhost:9092")
                .process(exchange -> {
                    ConsumerRecord<String, String> record = exchange.getIn().getBody(ConsumerRecord.class);
                    if (record != null) {
                        kafkaConsumerService.consume(record);

                        // Create a ProducerRecord for topic4 with the same key and value
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic4", record.key(), record.value());

                        // Set the producerRecord as the body of the exchange
                        exchange.getIn().setBody(producerRecord);
                    } else {
                        System.out.println("Received null message from Kafka topic");
                    }
                })
                .to("kafka:topic4?brokers=localhost:9092")
                .log("Message consumed from topic1 and written to topic4: ${body}");
    }
}
