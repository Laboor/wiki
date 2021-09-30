package com.avalon.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class JobConfig {
    private static final int poolSize = 5;                                 // 线程池大小
    private static final boolean waitForTasksToCompleteOnShutdown = true;  // 所有线程执行完关闭线程池
    private static final int awaitTerminationSeconds = 300;                // 等待的时间,超出后强制关闭
    private static final String threadNamePrefix = "taskExecutor-";        // 线程池名前缀

    @Bean("taskExecutor")
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(poolSize);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);   // 等待所有线程执行完关闭
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 初始化
        executor.initialize();
        return executor;
    }
}
