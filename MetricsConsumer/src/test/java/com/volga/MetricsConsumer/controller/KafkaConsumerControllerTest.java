package com.volga.MetricsConsumer.controller;

import com.volga.MetricsConsumer.dao.DataMetricsRepository;
import com.volga.MetricsConsumer.domain.DataMetric;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerControllerTest {

    @InjectMocks
    KafkaConsumerController kafkaConsumerController;

    @Mock
    DataMetricsRepository dataMetricsRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(kafkaConsumerController).build();
    }

    @Test
    void allMetrics() throws Exception {
        DataMetric first = DataMetric.builder()
                .memoryUsed("10")
                .startTime("10")
                .bufferedMemoryUsed("10")
                .build();
        DataMetric second = DataMetric.builder()
                .memoryUsed("20")
                .startTime("20")
                .bufferedMemoryUsed("20")
                .build();
        List<DataMetric> dataMetricList = new ArrayList<>();
        dataMetricList.add(first);
        dataMetricList.add(second);

        when(dataMetricsRepository.findAll()).thenReturn(dataMetricList);

        mockMvc.perform(get("/metrics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memoryUsed").value("10"))
                .andExpect(jsonPath("$[0].startTime").value("10"))
                .andExpect(jsonPath("$[0].bufferedMemoryUsed").value("10"))
                .andExpect(jsonPath("$[1].memoryUsed").value("20"))
                .andExpect(jsonPath("$[1].startTime").value("20"))
                .andExpect(jsonPath("$[1].bufferedMemoryUsed").value("20"));
        verify(dataMetricsRepository, times(1)).findAll();
    }

    @Test
    void metricById() throws Exception {
        DataMetric first = DataMetric.builder()
                .id(0)
                .memoryUsed("10")
                .startTime("10")
                .bufferedMemoryUsed("10")
                .build();

        when(dataMetricsRepository.findById(0L)).thenReturn(Optional.ofNullable(first));

        mockMvc.perform(get("/metrics/{id}", first.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memoryUsed").value("10"))
                .andExpect(jsonPath("$.startTime").value("10"))
                .andExpect(jsonPath("$.bufferedMemoryUsed").value("10"));
        verify(dataMetricsRepository, times(1)).findById(first.getId());
    }
}