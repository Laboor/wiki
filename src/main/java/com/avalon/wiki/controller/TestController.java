package com.avalon.wiki.controller;

import com.avalon.wiki.domain.Test;
import com.avalon.wiki.service.iface.IJobService;
import com.avalon.wiki.service.iface.ITestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    @Resource
    private IJobService jobService;


//    @PostMapping()
//    @PutMapping()
//    @DeleteMapping()
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/list")
    public List<Test> list() {
        return testService.list();
    }

    @GetMapping("/start")
    public void start() {
        System.out.println(jobService.resetJob("com.avalon.wiki.job.DynamicJobTest"));
    }

    @GetMapping("/stop")
    public void stop() {
        jobService.cancelJob("com.avalon.wiki.job.DynamicJobTest");
    }
}
