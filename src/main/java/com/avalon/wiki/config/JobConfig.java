package com.avalon.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class JobConfig {
    @Bean
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(10); // 设置线程池大小
        executor.setThreadNamePrefix("taskExecutor-"); // 设置线程名前缀
        executor.setWaitForTasksToCompleteOnShutdown(true); // 等待所有线程执行完
        executor.setAwaitTerminationSeconds(300); // 等待的时间,超出后强制关闭
        return executor;
    }
}
