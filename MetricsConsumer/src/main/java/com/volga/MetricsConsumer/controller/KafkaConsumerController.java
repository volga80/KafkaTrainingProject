package com.volga.MetricsConsumer.controller;

import com.volga.MetricsConsumer.dao.DataMetricsRepository;
import com.volga.MetricsConsumer.domain.DataMetric;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "KafkaConsumerController", description = "Контролер для получения данных " +
        "о производительности удаленного сервиса")
public class KafkaConsumerController {
    private final DataMetricsRepository dataMetricsRepository;

    @Operation(
            summary = "Список всех метрик производительности",
            description = "Метод возвращает список всех метрик о производительности " +
                    "удаленного сервиса, полученных из kafka"
    )
    @GetMapping("/metrics")
    public ResponseEntity<List<DataMetric>> getAllMetrics() {
        return ResponseEntity.ok(dataMetricsRepository.findAll());
    }

    @Operation(
            summary = "Возврат метрики по id",
            description = "Принимает id и возвращает неободимую метрику о производительности " +
                    "удаленного сервиса"
    )
    @GetMapping("/metrics/{id}")
    public ResponseEntity<Optional<DataMetric>> getMetricById(@PathVariable Long id) {
        return ResponseEntity.ok(dataMetricsRepository.findById(id));
    }
}
