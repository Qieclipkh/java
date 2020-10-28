package com.flink.time;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import com.flink.entity.StationLog;

/**
 * 每隔5秒统计一下最近10秒，每个基站的呼叫数量
 * <p>延迟2秒的数据不能丢弃</p>wartermark处理
 * <p>数据延迟2-5秒，再次触发窗口计算</p>allowedLateness
 * <p>数据延迟5秒以上，侧流输出</p>sideOutputLateData处理
 */
public class LatenessDataOnWindow {
    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));

    //侧输出流的标签
    private static final OutputTag<StationLog> TIMEOUT_TAG = new OutputTag<StationLog>("timeOutData") {
    };

    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //2、加载/读取数据
        DataStream<String> source = env.socketTextStream("172.16.32.38", 10010);
        //3、转换和处理数据
        SingleOutputStreamOperator<String> result = source
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
                .assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(5)))
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<StationLog>(Time.seconds(2)) {
                    @Override
                    public long extractTimestamp(StationLog element) {
                        return element.getCall_time();
                    }
                })
                .keyBy("sid")
                .window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
                .allowedLateness(Time.seconds(5))
                .sideOutputLateData(TIMEOUT_TAG)
                .aggregate(new MyAggregateFunction(), new OutputResultWindowFunction());
        //得到超出的数据，更新结果
        DataStream<StationLog> sideOutput = result.getSideOutput(TIMEOUT_TAG);
        sideOutput.print("超数据");
        //4、输出结果到指定地方
        result.print();
        //5、启动执行
        JobExecutionResult maxLongCallTime = env.execute("MaxLongCallTime");
    }

    static class MyAggregateFunction implements AggregateFunction<StationLog, Long, Long> {

        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(StationLog value, Long accumulator) {
            return accumulator + 1;
        }

        @Override
        public Long getResult(Long accumulator) {
            return accumulator;
        }

        @Override
        public Long merge(Long a, Long b) {
            return a + b;
        }
    }

    // 在窗口计算完成后执行
    static class OutputResultWindowFunction implements WindowFunction<Long, String, Tuple, TimeWindow> {

        @Override
        public void apply(Tuple tuple, TimeWindow window, Iterable<Long> input, Collector<String> out) throws Exception {
            Long value = input.iterator().next();
            StringBuilder sb = new StringBuilder();
            sb.append("窗口范围：");
            sb.append(window.getStart()).append(",").append(sdf.get().format(new Date(window.getStart())));
            sb.append("-----");
            sb.append(window.getEnd()).append(",").append(sdf.get().format(new Date(window.getEnd())));
            sb.append("\n");
            sb.append("ID：").append(((Tuple1) tuple).f0).append(",数量：").append(value);
            out.collect(sb.toString());
        }
    }

}
