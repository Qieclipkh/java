package com.cly;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author c
 * @create 2019/12/2 14:15
 */
public class Task implements Runnable {
    private final ThreadPoolExecutor executorService;
    private final int taskId;


    public Task(int i, ThreadPoolExecutor executorService) {
        this.taskId = i;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        System.out.println(System.currentTimeMillis() + ","
                + Thread.currentThread().getName()
                + ",taskId:" + taskId
                + ",taskCount:" + executorService.getTaskCount()
                + ",activeCount:" + executorService.getActiveCount()
                + ",poolSize:" + executorService.getPoolSize()
                + ",maximumPoolSize:" + executorService.getMaximumPoolSize());
    }
}
