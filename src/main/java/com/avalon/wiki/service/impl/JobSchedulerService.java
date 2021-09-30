package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.JobScheduler;
import com.avalon.wiki.mapper.JobSchedulerMapper;
import com.avalon.wiki.request.JobSchedulerReq;
import com.avalon.wiki.response.JobSchedulerResp;
import com.avalon.wiki.service.iface.IJobSchedulerService;
import com.avalon.wiki.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobSchedulerService implements IJobSchedulerService {
    @Resource
    private JobSchedulerMapper jobSchedulerMapper;

    @Override
    public List<JobSchedulerResp> list() {
        List<JobScheduler> jobSchedulerList = jobSchedulerMapper.findAll();
        List<JobSchedulerResp> respList = CopyUtil.copyList(jobSchedulerList, JobSchedulerResp.class);
        return respList;
    }

    @Override
    public JobSchedulerResp findByJobName(String name) {
        JobScheduler jobScheduler = jobSchedulerMapper.findByName(name);
        JobSchedulerResp jobSchedulerResp = CopyUtil.copy(jobScheduler, JobSchedulerResp.class);
        return jobSchedulerResp;
    }

    @Override
    public void update(JobSchedulerReq jobSchedulerReq) {
        JobScheduler jobScheduler = CopyUtil.copy(jobSchedulerReq, JobScheduler.class);
        jobSchedulerMapper.update(jobScheduler);
    }

    @Override
    public void addJob(JobSchedulerReq jobSchedulerReq) {
        JobScheduler jobScheduler = CopyUtil.copy(jobSchedulerReq, JobScheduler.class);
        jobSchedulerMapper.add(jobScheduler);
    }

    @Override
    public void deleteByJobName(String name) {
        jobSchedulerMapper.deleteByName(name);
    }
}
