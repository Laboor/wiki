package com.avalon.wiki.service.iface;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public interface IJobService {
    Map<String, ScheduledFuture<?>> getTaskList();
    boolean startJob(String jobName);
    boolean cancelJob(String jobName);
    boolean resetJob(String jobName);
    boolean hasJob(String jobName);
}
