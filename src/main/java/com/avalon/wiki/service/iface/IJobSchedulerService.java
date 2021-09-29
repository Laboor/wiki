package com.avalon.wiki.service.iface;

import com.avalon.wiki.request.JobSchedulerReq;
import com.avalon.wiki.response.JobSchedulerResp;

import java.util.List;

public interface IJobSchedulerService {
    List<JobSchedulerResp> list();

    JobSchedulerResp findByJobName(String name);

    void update(JobSchedulerReq req);

    void addJob(JobSchedulerReq req);

    void deleteByJobName(String name);
}
