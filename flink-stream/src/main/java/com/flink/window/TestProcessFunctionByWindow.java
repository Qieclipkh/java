package com.flink.window;

import java.util.Iterator;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import com.flink.entity.StationLog;

public class TestProcessFunctionByWindow {
    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
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
                }).map(new MapFunction<StationLog, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(StationLog value) throws Exception {
                return Tuple2.of(value.getSid(), 1);
            }
        }).keyBy(0)
                .timeWindow(Time.seconds(5))
                .process(new ProcessWindowFunction<Tuple2<String, Integer>, Tuple2<String, Long>, Tuple, TimeWindow>() {
                    // 窗口结束时候调用一次
                    @Override
                    public void process(Tuple key, Context context, Iterable<Tuple2<String, Integer>> in, Collector<Tuple2<String, Long>> out) throws Exception {
                        System.out.println("-----------------");
                        // 整个窗口的数据保存到迭代器中,存在很多数据
                        Long l = 0L;
                        Iterator<Tuple2<String, Integer>> iterator = in.iterator();
                        while (iterator.hasNext()) {
                            l += iterator.next().f1;
                        }
                        out.collect(Tuple2.of(((Tuple1) key).f0.toString(), l));

                    }
                })
        .print()
        ;
        //4、输出结果到指定地方
        //5、启动执行
        env.execute("TestProcessFunctionByWindow");
    }
}
