package com.flink.stream.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.RemoteStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

/**
 * Flink流计算-WordCount
 *
 * <pre>Linux启动socket端口：nc -lk 10010</pre>
 * <pre>Windows启动socket端口：nc -l 10010</pre>
 *
 * @Author changleying
 * @Date 2020/6/22 13:59
 **/
public class WordCount {
    public static void main(String[] args) throws Exception {
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2、加载/读取数据
        final DataStream<String> source = env.socketTextStream("172.16.32.38", 1234);
        //3、转换和处理数据
        final DataStream<Tuple2<String, Integer>> sum = source
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        //根据空白字符分割
                        //String[] s1 = value.split("\\s+");
                        // 根据非单词字符匹配
                        String[] s1 = value.split("\\W+");
                        for (String s : s1) {
                            if (s != null && s.length() > 0) {
                                out.collect(Tuple2.of(s, 1));
                            }
                        }
                    }
                })
                .keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                        return stringIntegerTuple2.f0;
                    }
                })
                .sum(1);
        //4、输出结果到指定地方
        sum.print();
        //5、启动执行
        env.execute();
    }
}
