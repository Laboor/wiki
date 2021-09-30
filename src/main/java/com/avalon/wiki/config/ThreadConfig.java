package com.avalon.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
    private static final int corePoolSize = 10;                      // 核心线程数（默认线程数）
    private static final int maxPoolSize = 100;                      // 最大线程数
    private static final int keepAliveTime = 10;                     // 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 200;                    // 缓冲队列数
    private static final int awaitTerminationSeconds = 60;           // 所有线程执行完等待时间
    private static final boolean waitForTasksToCompleteOnShutdown = true;  // 所有线程执行完关闭线程池
    private static final String threadNamePrefix = "asyncExecutor-"; // 线程池名前缀

    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);

        // 线程池对拒绝任务的处理策略
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 初始化
        executor.initialize();
        return executor;
    }
}
