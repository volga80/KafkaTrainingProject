package com.volga.MetricsConsumer.dao;

import com.volga.MetricsConsumer.domain.DataMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataMetricsRepository extends JpaRepository<DataMetric, Long> {
}
