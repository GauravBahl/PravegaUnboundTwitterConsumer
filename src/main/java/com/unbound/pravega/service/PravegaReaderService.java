package com.unbound.pravega.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unbound.pravega.config.constants.ConsumerConfig;
import com.unbound.pravega.model.TwitterPlaceMapper;
import com.unbound.pravega.model.TwitterResponseMapper;
import io.pravega.client.EventStreamClientFactory;
import io.pravega.client.stream.EventRead;
import io.pravega.client.stream.EventStreamReader;
import io.pravega.client.stream.ReaderConfig;
import io.pravega.client.stream.ReinitializationRequiredException;
import io.pravega.client.stream.impl.JavaSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PravegaReaderService {

    @Autowired
    private EventStreamClientFactory clientFactory;

    private static final int READER_TIMEOUT_MS = 2000;

    private static int bulkCount = 0;

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private ClassificationService classificationService;

    public void readStream() {
        final String readerGroup = ConsumerConfig.PRAVEGA_READER_GROUP;
        EventStreamReader<String> reader = clientFactory.createReader("reader",
                readerGroup,
                new JavaSerializer<String>(),
                ReaderConfig.builder().build());
        EventRead<String> event = null;
        do {
                try {
                    event = reader.readNextEvent(READER_TIMEOUT_MS);
                    if (event != null && event.getEvent() != null && event.getEvent().length()>10) {
                        String tweet = event.getEvent();
                        String classifiedTweet = classificationService.getTweetWithClassification(tweet);
                        TwitterResponseMapper responseMapper = gson.fromJson(classifiedTweet, TwitterResponseMapper.class);
                        responseMapper.setPlace(new TwitterPlaceMapper());
                        String tweetJson = gson.toJson(responseMapper);
                        elasticSearchService.saveData(tweetJson);
                    }
                } catch (ReinitializationRequiredException e) {
                    e.printStackTrace();
                }
            } while (true);
         }
    }
