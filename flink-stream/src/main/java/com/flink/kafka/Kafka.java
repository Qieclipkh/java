package com.flink.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;

public class Kafka {
    public static void main(String[] args) {
        //flink.partition-discovery.interval-millis
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");
        //动态发现分区，默认禁止，
        properties.setProperty("flink.partition-discovery.interval-millis", "30000");
        properties.setProperty("key.deserializer","StringDeserializer.class");
        properties.setProperty("value.deserializer","StringDeserializer.class");

        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty(" auto.commit.interval.ms", "30000");

        String topic = "ff";
        //TypeInformationSerializationSchema、JSONKeyValueDeserializationSchema、JsonDeserializationSchema

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer(topic, new SimpleStringSchema(), properties);
        consumer.setStartFromEarliest();
        Map<KafkaTopicPartition, Long> specificStartupOffsets = new HashMap<>();
        specificStartupOffsets.put(new KafkaTopicPartition(topic, 0), 23L);
        specificStartupOffsets.put(new KafkaTopicPartition(topic, 1), 31L);
        specificStartupOffsets.put(new KafkaTopicPartition(topic, 2), 42L);

        consumer.setStartFromSpecificOffsets(specificStartupOffsets);
        consumer.assignTimestampsAndWatermarks(new CustomWatermarkEmitter());
    }
}
