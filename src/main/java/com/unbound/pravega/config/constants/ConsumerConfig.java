package com.unbound.pravega.config.constants;

import java.util.UUID;

public class ConsumerConfig {
    public static final String PRAVEGA_READER_GROUP = UUID.randomUUID().toString().replace("-", "");
}
