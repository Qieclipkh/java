package com.flink.kafka;

import javax.annotation.Nullable;

import org.apache.flink.streaming.api.watermark.Watermark;

public class CustomWatermarkEmitter implements org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks {
    @Nullable
    public Watermark checkAndGetNextWatermark(Object lastElement, long extractedTimestamp) {
        return null;
    }

    @Override
    public long extractTimestamp(Object element, long previousElementTimestamp) {
        return 0;
    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return null;
    }
}
