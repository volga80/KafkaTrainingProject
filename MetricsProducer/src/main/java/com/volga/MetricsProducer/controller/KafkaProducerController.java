package com.volga.MetricsProducer.controller;

import com.volga.MetricsProducer.service.KafkaProducerService;
import com.volga.MetricsProducer.service.MetricsCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(
       name = "KafkaProducerController", description = "Контроллер для " +
        "отправки данных в kafka"
)
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;
    private final MetricsCollectionService metricsCollectionService;

    @Operation(
            summary = "Отправка данных в kafka",
            description = "При вызове этого метода вызывается метод класса MetricCollectionsService, " +
                    "который собирает данные о производительности сервиса. Данный контроллер отправляет эти данные " +
                    "в kafka"
    )
    @PostMapping("/metrics")
    @Transactional
    public ResponseEntity<Map<String, String>> sendMetricsToKafka() {
        Map<String, String> sendMetrics = metricsCollectionService.collectMetrics();
        log.info("Продюсер отправляет данные о работе приложения: использование памяти: {}" +
                        ", время запуска приложения: {}, использование буфферной памяти: {}",
                sendMetrics.get("memoryUsed"), sendMetrics.get("startTime"), sendMetrics.get("bufferedMemoryUsed"));
        kafkaProducerService.sendMessageInTopic(sendMetrics);
        return ResponseEntity.ok(sendMetrics);
    }

}
