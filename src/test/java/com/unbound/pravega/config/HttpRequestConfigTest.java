package com.unbound.pravega.config;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestConfigTest {

    @Test
    void getHttpRequestFactoryTest(){
        HttpRequestConfig config = new HttpRequestConfig();
        Assert.assertEquals(HttpComponentsClientHttpRequestFactory.class, config.getHttpRequestFactory().getClass());
    }

}