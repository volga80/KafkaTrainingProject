package com.volga.MetricsConsumer.service;

import com.volga.MetricsConsumer.dao.DataMetricsRepository;
import com.volga.MetricsConsumer.domain.DataMetric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final KafkaTemplate<String, Map<String, String>> kafkaConsumerTemplate;
    private final DataMetricsRepository dataMetricsRepository;

    @Transactional
    @KafkaListener(id = "statisticGroup", topics = "metrics-topic", groupId = "statistic_consumer")
    public void metricListener(Map<String, String> dataMetrics) {
        DataMetric metric = DataMetric.builder()
                .memoryUsed(dataMetrics.get("memoryUsed"))
                .startTime(dataMetrics.get("startTime"))
                .bufferedMemoryUsed(dataMetrics.get("bufferedMemoryUsed"))
                .build();
        log.info("Получены данные о работе приложения \"MetricsProducer\" со значениями: " +
                        "использование памяти: {}, " +
                        "время запуска приложения: {}, использование буфферной памяти: {}",
                metric.getMemoryUsed(), metric.getStartTime(), metric.getBufferedMemoryUsed());
        dataMetricsRepository.save(metric);
    }
}
