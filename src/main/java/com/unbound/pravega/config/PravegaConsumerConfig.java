package com.unbound.pravega.config;

import com.unbound.pravega.config.constants.ConsumerConfig;
import io.pravega.client.ClientConfig;
import io.pravega.client.EventStreamClientFactory;
import io.pravega.client.admin.ReaderGroupManager;
import io.pravega.client.admin.StreamManager;
import io.pravega.client.stream.ReaderGroupConfig;
import io.pravega.client.stream.ScalingPolicy;
import io.pravega.client.stream.Stream;
import io.pravega.client.stream.StreamConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ComponentScan("com.unbound.pravega")
public class PravegaConsumerConfig {

    @Value("${io.reflectoring.pravega.bootstrap-servers}")
    private String controllerStr;

    @Value("${io.reflectoring.pravega.scope}")
    private String scope;

    @Value("${io.reflectoring.pravega.streamName}")
    private  String streamName;

    @Bean
    public EventStreamClientFactory getEventStreamClient() {

        URI controllerURI = null;
        try {
            controllerURI = new URI(controllerStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        StreamManager streamManager = StreamManager.create(controllerURI);

        final boolean scopeIsNew = streamManager.createScope(scope);
        StreamConfiguration streamConfig = StreamConfiguration.builder()
                .scalingPolicy(ScalingPolicy.fixed(1))
                .build();
        final boolean streamIsNew = streamManager.createStream(scope, streamName, streamConfig);

        final String readerGroup = ConsumerConfig.PRAVEGA_READER_GROUP;
        final ReaderGroupConfig readerGroupConfig = ReaderGroupConfig.builder()
                .stream(Stream.of(scope, streamName))
                .build();

        try (ReaderGroupManager readerGroupManager = ReaderGroupManager.withScope(scope, controllerURI)) {
            readerGroupManager.createReaderGroup(readerGroup, readerGroupConfig);
        }
        EventStreamClientFactory clientFactory = EventStreamClientFactory.withScope(scope,
                ClientConfig.builder().controllerURI(controllerURI).build());

        return clientFactory;
    }
}
