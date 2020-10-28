package com.flink.kafka;


import java.io.IOException;

import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

public class KafkaObject extends AbstractDeserializationSchema {
    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        return null;
    }
}
