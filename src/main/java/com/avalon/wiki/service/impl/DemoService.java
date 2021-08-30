package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.Demo;
import com.avalon.wiki.mapper.DemoMapper;
import com.avalon.wiki.service.iface.IDemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService implements IDemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
