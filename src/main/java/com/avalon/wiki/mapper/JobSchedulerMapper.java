package com.avalon.wiki.mapper;

import com.avalon.wiki.domain.JobScheduler;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobSchedulerMapper {
    List<JobScheduler> findAll();
    JobScheduler findByJobName(String jobName);
    void updateByName(JobScheduler jobScheduler);
}