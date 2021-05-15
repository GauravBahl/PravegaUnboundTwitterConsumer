package com.unbound.pravega.config;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

class ExecutorServiceConfigTest {

    @Test
    void taskExecutorTest(){
       ExecutorServiceConfig config = new ExecutorServiceConfig();
       Assert.assertEquals(ThreadPoolExecutor.class, config.taskExecutor().getClass());
    }

}