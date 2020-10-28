package com.cly;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maximumPoolSize = 3;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        //BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        // CallerRunsPolicy AbortPolicy  DiscardPolicy DiscardOldestPolicy
        //RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        for(int i=0;i<20;i++){
            System.out.println(executorService.getQueue().size());
//            executorService.submit(new Task(i,executorService));
            executorService.submit(()->{
                System.out.println(System.currentTimeMillis() + ","
                        + Thread.currentThread().getName()
                        + ",taskCount:" + executorService.getTaskCount()
                        + ",activeCount:" + executorService.getActiveCount()
                        + ",poolSize:" + executorService.getPoolSize()
                        + ",maximumPoolSize:" + executorService.getMaximumPoolSize());
            });
        }
        executorService.shutdown();
        System.out.println("Hello World!");
    }
}
