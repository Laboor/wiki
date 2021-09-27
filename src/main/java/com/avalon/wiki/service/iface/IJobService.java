package com.avalon.wiki.service.iface;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public interface IJobService {
    Map<String, ScheduledFuture<?>> getTaskList();
    void startJob(String taskId);
    void cancelJob(String taskId);
    void resetJob(String taskId);
    boolean hasJob(String taskId);
}
