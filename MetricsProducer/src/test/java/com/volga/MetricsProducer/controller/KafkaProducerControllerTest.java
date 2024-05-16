package com.volga.MetricsProducer.controller;

import com.volga.MetricsProducer.service.KafkaProducerService;
import com.volga.MetricsProducer.service.MetricsCollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class KafkaProducerControllerTest {

    @InjectMocks
    KafkaProducerController kafkaProducerController;
    @Mock
    MetricsCollectionService metricsCollectionService;
    @Mock
    KafkaProducerService kafkaProducerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(kafkaProducerController).build();
    }

    @Test
    void sendingMetricsToKafka() throws Exception {
        Map<String, String> testMetrics = new HashMap<>();
        testMetrics.put("one", "one");
        testMetrics.put("two", "two");

        when(metricsCollectionService.collectMetrics()).thenReturn(testMetrics);

        mockMvc.perform(MockMvcRequestBuilders.post("/metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.one").value("one"))
                .andExpect(jsonPath("$.two").value("two"));
        verify(kafkaProducerService, times(1)).sendMessageInTopic(testMetrics);
    }
}