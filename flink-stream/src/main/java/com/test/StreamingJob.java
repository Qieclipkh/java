/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.test.source.TestSource;


public class StreamingJob {

    public static void main(String[] args) throws Exception {
        //执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        //加载数据
        DataStream<Tuple2<Integer, Integer>> source = env.addSource(new TestSource());
        //数据转换操作
        KeyedStream<Tuple2<Integer, Integer>, Tuple> keyedStream = source.keyBy(0);
        /**
         *
         * 时间窗口-滚动窗口
         *
         * 计算最近4秒得数值总和
         */
        WindowedStream timeWinTStream = keyedStream.timeWindow(Time.seconds(4));
        keyedStream.window(TumblingProcessingTimeWindows.of(Time.seconds(4)));
        /**
         *
         * 时间窗口-滑动窗口
         *
         * 计算最近4秒的数值求和,但每2秒滑动一次
         * 2秒输出一次结果
         *
         */
        //WindowedStream timeWinSStream = keyedStream.timeWindow(Time.seconds(4), Time.seconds(2));

        /**
         * 计数窗口-滚动窗口
         *
         * 最近4条数据的数值求和
         */
        //WindowedStream countWinTStream = keyedStream.countWindow(4);
        /**
         * 计数窗口-滑动窗口
         */
        //WindowedStream countWinSStream = keyedStream.countWindow(4, 2);


        /**
         * 全局窗口
         */
        //WindowedStream globalWinSStream = keyedStream.window(GlobalWindows.create());

        // 求和
        DataStream sum = timeWinTStream.sum(1);
        //输出结果数据
        sum.print();
        // 触发执行
        env.execute("样例");
    }
}
