package com.volga.MetricsProducer.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Map<String, String>> kafkaProducerTemplate;

    public void sendMessageInTopic(Map<String, String> data) {
        kafkaProducerTemplate.send("metrics-topic", data);
    }
}
