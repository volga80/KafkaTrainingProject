package com.volga.MetricsProducer;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class MetricsProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsProducerApplication.class, args);
    }
}