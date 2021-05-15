package com.unbound.pravega.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    private static final int MAX_NUM_OF_THREADS = 10;

    @Bean
    public ExecutorService taskExecutor(){
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
        return executorService;
    }
}
