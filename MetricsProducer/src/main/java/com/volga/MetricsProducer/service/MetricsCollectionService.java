package com.volga.MetricsProducer.service;


import java.util.Map;

public interface MetricsCollectionService {
    Map<String, String> collectMetrics();
}
