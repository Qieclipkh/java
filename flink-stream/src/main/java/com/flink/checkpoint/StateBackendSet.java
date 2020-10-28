package com.flink.checkpoint;

import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StateBackendSet {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /**
         * 10M的数据
         */
        env.setStateBackend(new MemoryStateBackend(10*1024*1024));
        env.setStateBackend(new FsStateBackend("hdfs://master:9000/flink/checkpoint/fsb1"));
        env.setStateBackend(new RocksDBStateBackend("hdfs://master:9000/flink/checkpoint/rdb1"));
        env.execute("StateBackendSet");
    }
}
