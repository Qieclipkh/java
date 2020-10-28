package com.flink.time;

import javax.annotation.Nullable;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.flink.entity.StationLog;

public class TestEventTime {
    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //2、加载/读取数据
        DataStream<String> source = env.socketTextStream("172.16.32.38", 10010);
        //3、转换和处理数据
        DataStream<StationLog> mapStream = source
                .map(new MapFunction<String, StationLog>() {
                    @Override
                    public StationLog map(String value) throws Exception {
                        String[] arr = value.split(",");
                        return new StationLog(arr[0], arr[1], arr[2], arr[3], Long.parseLong(arr[4]), Integer.parseInt(arr[5]));
                    }
                });
        mapStream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<StationLog>(Time.seconds(3)) {
            //延迟3秒处理
            @Override
            public long extractTimestamp(StationLog element) {
                return element.getCall_time();
            }
        });
        //4、输出结果到指定地方
        //5、启动执行
        env.execute("TestEventTime");
    }

    // 周期性生成watermark,默认是100ms
    static class MyPeriodicWatermark implements AssignerWithPeriodicWatermarks<StationLog> {

        public MyPeriodicWatermark() {
        }

        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            return null;
        }

        @Override
        public long extractTimestamp(StationLog element, long previousElementTimestamp) {
            return element.getCall_time();
        }
    }


    static class MyPunctuatedWatermark implements AssignerWithPunctuatedWatermarks<StationLog> {

        private Long delay;

        private Long maxTime;

        public MyPunctuatedWatermark(Long delay) {
            this.delay = delay;
        }


        @Nullable
        @Override
        public Watermark checkAndGetNextWatermark(StationLog lastElement, long extractedTimestamp) {

            if (lastElement.getSid().equals("station_1")) {
                maxTime = Math.max(maxTime, extractedTimestamp);
                return new Watermark(maxTime - delay);
            }
            return null;
        }

        @Override
        public long extractTimestamp(StationLog element, long previousElementTimestamp) {
            return Math.max(element.getCall_time(),previousElementTimestamp);
        }
    }
}
