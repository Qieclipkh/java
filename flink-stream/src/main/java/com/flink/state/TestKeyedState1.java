package com.flink.state;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import com.flink.entity.StationLog;

public class TestKeyedState1 {
    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、加载/读取数据
        String filePath = TestKeyedState1.class.getResource("/station.log").getPath();
        DataStream<String> dataStream = env.readTextFile(filePath);
        //3、转换和处理数据
        DataStream<StationLog> map = dataStream.map(new MapFunction<String, StationLog>() {
            @Override
            public StationLog map(String value) throws Exception {
                String[] arr = value.split(",");
                return new StationLog(arr[0], arr[1], arr[2], arr[3], Long.parseLong(arr[4]), Integer.parseInt(arr[5]));
            }
        });
        DataStream<Tuple2<String, Long>> result = map.keyBy("call_out").flatMap(new CallIntervalFunction());
        //4、输出结果到指定地方
        result.print();
        //5、启动执行
        env.execute("TestKeyedState1");

    }

    static class CallIntervalFunction extends RichFlatMapFunction<StationLog, Tuple2<String, Long>> {
        private ValueState<Long> preCallTimeState;

        @Override
        public void open(Configuration parameters) throws Exception {
            preCallTimeState = getRuntimeContext().getState(new ValueStateDescriptor<Long>("", Long.class));
        }

        @Override
        public void flatMap(StationLog value, Collector<Tuple2<String, Long>> out) throws Exception {
            Long preCallTime = preCallTimeState.value();
            if (preCallTime == null || preCallTime == 0) {
                preCallTimeState.update(value.getCall_time());
            } else {
                Long interval = value.getCall_time() - preCallTime;
                out.collect(Tuple2.of(value.getCall_in(), interval));
            }
        }
    }

}
