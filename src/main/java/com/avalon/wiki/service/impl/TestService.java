package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.Test;
import com.avalon.wiki.job.DynamicJobTest;
import com.avalon.wiki.mapper.TestMapper;
import com.avalon.wiki.service.iface.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService implements ITestService {
    private static final Logger LOG = LoggerFactory.getLogger(TestService.class);

    @Resource
    private TestMapper testMapper;

    @Override
    public List<Test> list() {
        return testMapper.list();
    }

    @Async("asyncExecutor")
    public void foo() {
        LOG.info("11111111111111111111111111111111111");
    }
}