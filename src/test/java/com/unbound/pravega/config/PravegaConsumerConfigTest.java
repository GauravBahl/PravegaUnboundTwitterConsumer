package com.unbound.pravega.config;

import io.pravega.client.EventStreamClientFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class PravegaConsumerConfigTest {


    private String controllerStr = "tcp://127.0.0.1:9090";
    private String scope="examples";
    private  String streamName="helloStream";

    @Test
    void getEventStreamClientTest(){
        PravegaConsumerConfig config = new PravegaConsumerConfig();
        ReflectionTestUtils.setField(config, "controllerStr",controllerStr);
        ReflectionTestUtils.setField(config, "scope",scope);
        ReflectionTestUtils.setField(config, "streamName",streamName);

        //Assert.assertEquals(EventStreamClientFactory.class, config.getEventStreamClient().);
    }



}