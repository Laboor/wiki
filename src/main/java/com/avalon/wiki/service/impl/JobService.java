package com.avalon.wiki.service.impl;

import com.avalon.wiki.constant.Constant;
import com.avalon.wiki.domain.JobScheduler;
import com.avalon.wiki.mapper.JobSchedulerMapper;
import com.avalon.wiki.service.iface.IJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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
    private JobSchedulerMapper jobSchedulerMapper;
    @Resource
    private ApplicationContext context;
    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    // 开机自动从数据库加载任务
    @PostConstruct
    private void init() {
        for (JobScheduler jobScheduler : jobSchedulerMapper.findAll()) {
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
    public boolean startJob(String jobName) {
        if (taskMap.containsKey(jobName)) {
            LOG.error("Job[{}] was already run.", jobName);
            return false;
        }
        JobScheduler jobScheduler = jobSchedulerMapper.findByJobName(jobName);
        if (jobScheduler != null) {
            jobScheduler.setStatus(Constant.JOB_ENABLE);
            jobSchedulerMapper.updateByJobName(jobScheduler);
            executeJob(jobScheduler);
        } else {
            LOG.error("Job[{}] does not exist in the database.", jobName);
            return false;
        }
        return true;
    }

    @Override
    public boolean cancelJob(String jobName) {
        ScheduledFuture future = taskMap.get(jobName);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(jobName);
            JobScheduler jobScheduler = new JobScheduler();
            jobScheduler.setJobName(jobName);
            jobScheduler.setStatus(Constant.JOB_DISABLE);
            jobSchedulerMapper.updateByJobName(jobScheduler);
            LOG.info("Job cancel:{}.", jobName);
        } else {
            LOG.info("Job:{} is not running.", jobName);
            return false;
        }
        return true;
    }

    @Override
    public boolean resetJob(String jobName) {
        cancelJob(jobName);
        return startJob(jobName);
    }

    @Override
    public boolean hasJob(String jobName) {
        return taskMap.containsKey(jobName);
    }
}
