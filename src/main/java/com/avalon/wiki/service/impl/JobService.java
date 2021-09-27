package com.avalon.wiki.service.impl;

import com.avalon.wiki.constant.Constant;
import com.avalon.wiki.domain.JobScheduler;
import com.avalon.wiki.service.iface.IJobSchedulerService;
import com.avalon.wiki.service.iface.IJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class JobService implements IJobService {
    private static final Logger LOG = LoggerFactory.getLogger(JobService.class);

    @Resource
    private IJobSchedulerService jobSchedulerService;
    @Resource
    private ApplicationContext context;
    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    // 开机自动从数据库加载任务
    @PostConstruct
    private void init() {
        for (JobScheduler jobScheduler : jobSchedulerService.list()) {
            executeJob(jobScheduler);
        }
    }

    private void executeJob(JobScheduler jobScheduler) {
        Class<?> clazz;
        Object job;
        try {
            clazz = Class.forName(jobScheduler.getJobName());
            job = context.getBean(clazz);
            if (Constant.JOB_ENABLE.equals(jobScheduler.getStatus())) {
                ScheduledFuture future = threadPoolTaskScheduler.schedule(((Runnable) job), new CronTrigger(jobScheduler.getCronExpression()));
                taskMap.put(jobScheduler.getJobName(), future);
                LOG.info("Job:{} is running...", jobScheduler.getJobName());
            } else {
                LOG.info("Job:{} is disable.", jobScheduler.getJobName());
            }
        } catch (ClassNotFoundException e) {
            LOG.error("Can not found jobSchedule class: {}.", jobScheduler.getJobName(), e);
        } catch (ClassCastException e) {
            LOG.error("The jobSchedule class[{}] does not implements Runnable.", jobScheduler.getJobName(), e);
        } catch (IllegalArgumentException e) {
            LOG.error("Job:{} cronExpression is error: {}.", jobScheduler.getJobName(), jobScheduler.getCronExpression(), e);
        }
    }

    @Override
    public Map<String, ScheduledFuture<?>> getTaskList() {
        return taskMap;
    }

    @Override
    public void startJob(String jobName) {
        if (taskMap.containsKey(jobName)) {
            throw new SchedulingException("Job[" + jobName + "] was already run.");
        }
        JobScheduler jobScheduler = jobSchedulerService.findByName(jobName);
        if (jobScheduler != null) {
            jobScheduler.setStatus(Constant.JOB_ENABLE);
            jobSchedulerService.updateByName(jobScheduler);
            executeJob(jobScheduler);
        } else {
            throw new SchedulingException("Job[" + jobName + "]does not exist in the database.");
        }
    }

    @Override
    public void cancelJob(String jobName) {
        ScheduledFuture future = taskMap.get(jobName);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(jobName);
            JobScheduler jobScheduler = new JobScheduler();
            jobScheduler.setJobName(jobName);
            jobScheduler.setStatus(Constant.JOB_DISABLE);
            jobSchedulerService.updateByName(jobScheduler);
            LOG.info("Job cancel:{}.", jobName);
        } else {
            LOG.info("Job:{} is not running.", jobName);
        }
    }

    @Override
    public void resetJob(String jobId) {
        cancelJob(jobId);
        startJob(jobId);
    }

    @Override
    public boolean hasJob(String jobId) {
        return taskMap.containsKey(jobId);
    }
}
