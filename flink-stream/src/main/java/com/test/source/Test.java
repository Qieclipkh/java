package com.test.source;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author changleying
 * @Date 2020/6/17 15:03
 **/
public class Test implements SourceFunction<Tuple3<Integer, Integer, Date>> {

    private volatile boolean isRunning = true;
    private int[] event = {0, 9, 8, 7, 4, 6, 5, 6, 4, 3, 2, 3, 1};


    private int index = event.length - 1;

    private boolean start = false;

    @Override
    public void run(SourceContext<Tuple3<Integer, Integer, Date>> sourceContext) throws Exception {
        while (isRunning && start) {
            if (index == 0) {
                index = event.length - 1;
            }
            System.out.println("生产数据====" + event[index]);
            sourceContext.collect(Tuple3.of(1, event[index], new Date()));
            index--;
            Thread.sleep(1 * 1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}