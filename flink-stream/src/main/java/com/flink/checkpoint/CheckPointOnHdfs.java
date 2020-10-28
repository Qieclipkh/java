package com.flink.checkpoint;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class CheckPointOnHdfs {

    public static void main(String[] args) throws Exception {
        /**
         * nc -lk 1234
         * --ip 172.16.32.38 --port 1234
         */

        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String ip = parameterTool.get("ip","172.16.32.38");
        int port = parameterTool.getInt("port",1234);
        //1、获取执行坏境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //
        env.enableCheckpointing(5000);
        StateBackend backend = new FsStateBackend("hdfs://master:9000/flink/checkpoint/cp1");
        env.setStateBackend(backend);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(5000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.setParallelism(1);
        //2、加载/读取数据
        final DataStream<String> source = env.socketTextStream(ip, port);
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
                .keyBy(0)
                .sum(1);
        //4、输出结果到指定地方
        sum.print("结果");
        //5、启动执行
        env.execute("流计算-Wordcount");
    }
}
