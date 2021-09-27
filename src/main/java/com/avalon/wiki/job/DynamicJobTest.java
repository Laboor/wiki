package com.avalon.wiki.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DynamicJobTest implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String dateString = formatter.format(new Date());
        LOG.info("定时任务1： {}", dateString);
    }
}
