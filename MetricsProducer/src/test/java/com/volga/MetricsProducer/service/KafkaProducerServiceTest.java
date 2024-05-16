package com.volga.MetricsProducer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @InjectMocks
    KafkaProducerService kafkaProducerService;

    @Mock
    KafkaTemplate<String, Map<String, String>> template;

    @Test
    void sendingMessageInTopic() {
        Map<String, String> testData = new HashMap<>();
        testData.put("one", "first");
        testData.put("two", "second");

        kafkaProducerService.sendMessageInTopic(testData);

        verify(template, times(1)).send("metrics-topic", testData);
    }
}