package com.unbound.pravega.service;

import com.unbound.pravega.config.ExecutorServiceConfig;
import com.unbound.pravega.config.HttpRequestConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class ElasticSearchService {
    @Value("${es.url}")
    private String ES_URL;

    @Value("${es.index}")
    private String index;

    @Autowired
    HttpRequestConfig httpRequestConfig;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Autowired
    private ExecutorServiceConfig executorServiceConfig;

    public void saveDataInParallel(String json){
        ExecutorService executorService = executorServiceConfig.taskExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                saveData(json);
            }
        });

        //executorService.shutdown();

    }

    public void saveData(String json) {
        System.out.println(json);
        json = addTimeStamp(json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
        headers.add("Accept", "*/*");
        headers.add("Cache-Control", "no-cache");

        HttpEntity<String> request = new HttpEntity<String>(json, headers);

        ResponseEntity<String> response
                = new RestTemplate(httpRequestConfig.getHttpRequestFactory()).exchange(
                ES_URL + "/" + index + "/_doc", HttpMethod.POST, request, String.class);
        System.out.println("Elastic Search: " + response.getBody());
    }

    public void saveBulk(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String doc : list) {
            doc = addTimeStamp(doc);
            builder.append(doc);
            builder.append(System.getProperty("line.separator"));
        }


        String json = builder.toString();

        System.out.println(json);

        //System.out.println(json);
        //json = addTimeStamp(json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
        headers.add("Accept", "*/*");
        headers.add("Cache-Control", "no-cache");

        HttpEntity<String> request = new HttpEntity<String>(json, headers);

        ResponseEntity<String> response
                = new RestTemplate(httpRequestConfig.getHttpRequestFactory()).exchange(
                ES_URL + "/" + index + "/_doc/_bulk", HttpMethod.POST, request, String.class);

        System.out.println("Elastic Search: " + response.getBody());
    }


    @SuppressWarnings("deprecation")
    private String addTimeStamp(String json) throws JSONException {
        Date date = new Date();
        JSONObject obj = new JSONObject(json);
        obj.put("@timestamp", simpleDateFormat.format(date));
        return obj.toString();
    }

}
