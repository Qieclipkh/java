package com.flink.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;

import com.flink.entity.StationLog;

/**
 * 每隔5秒统计一下最近10秒内，每个基站中通话时间最长的一次通话发生时间，主叫号码，被叫号码，通话时长，并且发生的时间范围
 */
public class MaxLongCallTime {
    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));

    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //2、加载/读取数据
        DataStream<String> source = env.socketTextStream("172.16.32.38", 10010);
        //3、转换和处理数据
        source
                .map(new MapFunction<String, StationLog>() {
                    @Override
                    public StationLog map(String value) throws Exception {
                        String[] arr = value.split(",");
                        return new StationLog(arr[0], arr[1], arr[2], arr[3], Long.parseLong(arr[4]), Integer.parseInt(arr[5]));
                    }
                }).filter(new FilterFunction<StationLog>() {
            @Override
            public boolean filter(StationLog value) throws Exception {
                return "success".equals(value.getCall_type());
            }
        })
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<StationLog>(Time.seconds(1)) {
                    @Override
                    public long extractTimestamp(StationLog element) {
                        return element.getCall_time();
                    }
                })
                .keyBy("sid")
                .timeWindow(Time.seconds(10), Time.seconds(5))
                .reduce(new MyReduceFunction(), new MyWindowFunction())
                //4、输出结果到指定地方
                .print()
        ;
        //5、启动执行
        env.execute("MaxLongCallTime");
    }

    static class MyReduceFunction implements ReduceFunction<StationLog> {
        @Override
        public StationLog reduce(StationLog value1, StationLog value2) throws Exception {
            if (value1.getDuration() > value2.getDuration()) {
                return value1;
            } else {
                return value2;
            }
        }
    }

    // 在窗口计算完成后执行
    static class MyWindowFunction implements WindowFunction<StationLog, String, Tuple, TimeWindow> {
        @Override
        public void apply(Tuple tuple, TimeWindow window, Iterable<StationLog> input, Collector<String> out) throws Exception {
            StringBuilder sb = new StringBuilder();
            sb.append("窗口范围：").append(sdf.get().format(new Date(window.getStart()))).append("-----").append(sdf.get().format(new Date(window.getEnd()))).append("\n");
            sb.append("通话日志：").append(input.iterator().next().toString());
            out.collect(sb.toString());
        }
    }

}
