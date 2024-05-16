package com.volga.MetricsConsumer.service;

import com.volga.MetricsConsumer.dao.DataMetricsRepository;
import com.volga.MetricsConsumer.domain.DataMetric;
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
class KafkaConsumerServiceTest {

    @InjectMocks
    KafkaConsumerService kafkaConsumerService;

    @Mock
    DataMetricsRepository dataMetricsRepository;

    @Mock
    KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @Test
    void metricListener() {
        Map<String, String> testDataMetrics = new HashMap<>();
        testDataMetrics.put("memoryUsed", "1024");
        testDataMetrics.put("startTime", "2024-05-20 08:00:00");
        testDataMetrics.put("bufferedMemoryUsed", "512");
        DataMetric testMetric = DataMetric.builder()
                .id(0L)
                .memoryUsed(testDataMetrics.get("memoryUsed"))
                .startTime(testDataMetrics.get("startTime"))
                .bufferedMemoryUsed(testDataMetrics.get("bufferedMemoryUsed"))
                .build();

        kafkaConsumerService.metricListener(testDataMetrics);

        verify(dataMetricsRepository, times(1)).save(testMetric);
    }
}