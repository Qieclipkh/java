package com.flink.fun;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class TestFunction {
    public static void main(String[] args) {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、加载/读取数据
        String path = TestFunction.class.getResource("/").getPath();
        DataStream<String> dataStream = env.readTextFile("");
        //3、转换和处理数据
        dataStream.keyBy(0).process(new KeyedProcessFunction<Tuple, String, Object>() {
            @Override
            public void processElement(String value, Context ctx, Collector<Object> out) throws Exception {
            }
        });
        //4、输出结果到指定地方
        //5、启动执行
    }
}
