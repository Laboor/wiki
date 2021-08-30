package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.Test;
import com.avalon.wiki.mapper.TestMapper;
import com.avalon.wiki.service.iface.ITestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService implements ITestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}