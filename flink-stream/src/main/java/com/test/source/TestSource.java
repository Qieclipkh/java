package com.test.source;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author changleying
 * @Date 2020/6/17 10:37
 **/
public class TestSource implements SourceFunction<Tuple2<Integer, Integer>> {

    private volatile boolean isRunning = true;
    private int[] event = {0, 9, 8, 7, 4, 6, 5, 6, 4, 3, 2, 3, 1};

    private int index = event.length - 1;

    private boolean start = true;

    @Override
    public void run(SourceContext<Tuple2<Integer, Integer>> sourceContext) throws Exception {
        //start();
        while (isRunning && start) {
            if (index == 0) {
                index = event.length - 1;
            }
            System.out.println("生产数据===="+new Date() +","+ event[index]);
            sourceContext.collect(Tuple2.of(1, event[index]));
            index--;
            Thread.sleep(1 * 1000);
        }
    }

    /**
     *
     */
    private void start() {
        int second = LocalDateTime.now().getSecond();
        while (!(start = second % 4 == 0)) {
            second = LocalDateTime.now().getSecond();
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
