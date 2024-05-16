package com.volga.MetricsProducer.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class MetricsCollectionsServiceImpl implements MetricsCollectionService {

    private final MetricsEndpoint metricsEndpoint;

    @Override
    public Map<String, String> collectMetrics() {
        String memoryUsed = metricsEndpoint.metric("jvm.memory.used", null)
                .getMeasurements().get(0).getValue().toString();
        String startTime = metricsEndpoint.metric("application.started.time", null)
                .getMeasurements().get(0).getValue().toString();
        String bufferedMemoryUsed = metricsEndpoint.metric("jvm.buffer.memory.used", null)
                .getMeasurements().get(0).getValue().toString();
        Map<String, String> dataSendMetric = new HashMap<>();
        dataSendMetric.put("memoryUsed", memoryUsed);
        dataSendMetric.put("startTime", startTime);
        dataSendMetric.put("bufferedMemoryUsed", bufferedMemoryUsed);
        return dataSendMetric;
    }
}
