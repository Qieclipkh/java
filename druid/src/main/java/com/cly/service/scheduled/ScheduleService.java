package com.cly.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleService {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    private ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return  new ThreadPoolTaskScheduler();
    }
    private ScheduledFuture future;
    public void startCron(){
        String cron ="0/30 * * * * ?";
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("启动定时任务");
            }
        }, triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext));
    }


    public void stopCron(){
        if(future !=null){
            future.cancel(true);
            System.out.println("停止定时任务");
        }
    }

}
