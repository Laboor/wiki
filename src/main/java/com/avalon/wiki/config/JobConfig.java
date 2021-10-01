package com.avalon.wiki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class JobConfig {
    @Value("${threadPool.taskThread.poolSize}")
    private int poolSize;                                 // 线程池大小

    @Value("${threadPool.taskThread.waitForTasksToCompleteOnShutdown}")
    private boolean waitForTasksToCompleteOnShutdown;     // 所有线程执行完关闭线程池

    @Value("${threadPool.taskThread.awaitTerminationSeconds}")
    private int awaitTerminationSeconds;                  // 等待的时间,超出后强制关闭

    @Value("${threadPool.taskThread.threadNamePrefix}")
    private String threadNamePrefix;                      // 线程池名前缀

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
