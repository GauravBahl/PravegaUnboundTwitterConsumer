package com.unbound.pravega.service;

import com.google.gson.Gson;
import com.unbound.pravega.config.HttpRequestConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClassificationService {

    @Value("${cls.url}")
    private String CLASSIFICATION_API_URL;

    @Autowired
    private HttpRequestConfig httpRequestConfig;

    Gson gson = new Gson();

    Map<String, String> map = new HashMap<>();

    public String classifyTweetText(String tweet){

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Accept", "*/*");
            headers.add("Cache-Control", "no-cache");

            map.put("tweettext", tweet);
            String requestJson = gson.toJson(map).toString();

            //System.out.println(requestJson);
            HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

            ResponseEntity<String> responseEntity
                    = new RestTemplate(httpRequestConfig.getHttpRequestFactory()).exchange(
                    CLASSIFICATION_API_URL, HttpMethod.POST, request, String.class);

            String response = responseEntity.getBody();
            return response;

        }catch(Exception e){
            return "related";
        }
    }

    //preds_label
    public String getTweetWithClassification(String tweet){
        JSONObject json=new JSONObject(tweet);
        String tweetText = json.get("text").toString();

        String classifiedResult = classifyTweetText(tweetText);
        json.put("preds_label", classifiedResult);

        return json.toString();
    }
}
