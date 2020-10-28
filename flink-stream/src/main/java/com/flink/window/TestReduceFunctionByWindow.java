package com.flink.window;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.flink.entity.StationLog;

public class TestReduceFunctionByWindow {
    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、加载/读取数据
        DataStream<String> source = env.socketTextStream("172.16.32.38", 10010);
        //3、转换和处理数据
        DataStream<Tuple2<String, Integer>> result = source
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
                //.reduce((a,b)->Tuple2.of(a.f0,a.f1+b.f1))
                .reduce(new MyReduceFunction());
        //4、输出结果到指定地方
        result.print("结果");
        //5、启动执行
        env.execute("TestReduceFunctionByWindow");
    }

    static class MyReduceFunction implements ReduceFunction<Tuple2<String, Integer>> {

        @Override
        public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
            return Tuple2.of(value1.f0, value1.f1 + value2.f1);
        }
    }
}
