package com.flink.checkpoint;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CheckPointSet {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /**
         * 每隔5分钟进行一次checkpoint
         */
        env.enableCheckpointing(5*60*1000, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointConfig.DEFAULT_MODE);
        /**
         * checkpoint超时时间
         */
        env.getCheckpointConfig().setCheckpointTimeout(10*60*100);
        /**
         * checkpoint之间最小时间间隔
         */
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(5*60*1000);
        /**
         * checkpoint最大并行的数量
         */
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(5);
        /**
         * 是否删除checkpoint中保存的数据
         * RETAIN_ON_CANCELLATION：表示一旦flink处理程序被cancel后，会保留checkpoint数据，以便根据实际需要恢复到指定的checkpoint
         * DELETE_ON_CANCELLATION：表示一旦flink处理程序被cancel后，会删除checkpoint数据，只有Job执行失败的时候才会保存checkpoint
         *
         */
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
        /**
         * 容忍检查的失败数，超过这个数量则系统自动关闭和停止任务
         */
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(1);
        env.execute("CheckPointSet");
    }
}
