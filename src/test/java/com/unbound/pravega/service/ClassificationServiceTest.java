package com.unbound.pravega.service;

import com.unbound.pravega.config.HttpRequestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class ClassificationServiceTest {


    private String CLASSIFICATION_API_URL = "http://localhost:5001/cls";


    @Test
    void classifyTweetText() {
        ClassificationService service = new ClassificationService();
        ReflectionTestUtils.setField(service, "CLASSIFICATION_API_URL",CLASSIFICATION_API_URL);
        ReflectionTestUtils.setField(service, "httpRequestConfig", new HttpRequestConfig());

        String response = service.classifyTweetText("Test text");
        Assert.assertEquals(String.class, response.getClass());
    }
}