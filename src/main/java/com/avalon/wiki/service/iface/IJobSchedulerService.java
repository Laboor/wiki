package com.avalon.wiki.service.iface;

import com.avalon.wiki.domain.JobScheduler;

import java.util.List;

public interface IJobSchedulerService {
    List<JobScheduler> list();
    JobScheduler findByName(String name);
    void updateByName(JobScheduler jobScheduler);
}
