package com.shooter.springboot.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import java.util.concurrent.Executor;

/**
 * 默认线程池配置
 */
@Slf4j
@SpringBootConfiguration
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer
{
    /**
     * 定时任务以多线程的形式进行
     * */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        TaskScheduler taskScheduler = taskScheduler();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    /**
     * 配置默认线程池
     * */
    @Bean(destroyMethod="shutdown")
    public ThreadPoolTaskScheduler taskScheduler()
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor()
    {
        Executor executor = taskScheduler();
        return executor;
    }

    /**
     * 异步任务中异常处理
     * */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
    {
        return (arg0, arg1, arg2) -> {
            log.error("方法{}- 错误提示{}",arg1.getName(),arg0.getMessage());
        };
    }
}
