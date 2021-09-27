package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.JobScheduler;
import com.avalon.wiki.mapper.JobSchedulerMapper;
import com.avalon.wiki.service.iface.IJobSchedulerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobSchedulerService implements IJobSchedulerService {
    @Resource
    private JobSchedulerMapper jobSchedulerMapper;

    public List<JobScheduler> list() {
        return jobSchedulerMapper.findAll();
    }

    public JobScheduler findByName(String name) {
        return jobSchedulerMapper.findByJobName(name);
    }

    public void updateByName(JobScheduler jobScheduler) {
        jobSchedulerMapper.updateByName(jobScheduler);
    }
}
