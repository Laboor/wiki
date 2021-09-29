package com.avalon.wiki.controller;

import com.avalon.wiki.request.JobSchedulerReq;
import com.avalon.wiki.response.CommonResp;
import com.avalon.wiki.response.JobSchedulerResp;
import com.avalon.wiki.service.iface.IJobSchedulerService;
import com.avalon.wiki.service.iface.IJobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    private IJobService jobService;
    @Resource
    private IJobSchedulerService jobSchedulerService;

    @GetMapping("/list")
    public CommonResp<List<JobSchedulerResp>> list() {
        CommonResp<List<JobSchedulerResp>> resp = new CommonResp<>();
        List<JobSchedulerResp> list = jobSchedulerService.list();
        resp.setData(list);
        resp.setMessage("成功");
        return resp;
    }

    @PatchMapping("/start")
    public CommonResp startJobByName(JobSchedulerReq jobSchedulerReq) {
        CommonResp resp = new CommonResp<>();
        boolean result = jobService.startJob(jobSchedulerReq.getJobName());
        String msg = result ? "任务启动成功" : "任务启动失败";
        resp.setMessage(msg);
        resp.setSuccess(result);
        return resp;
    }

    @PatchMapping("/cancel")
    public CommonResp cancelJobByName(JobSchedulerReq jobSchedulerReq) {
        CommonResp resp = new CommonResp<>();
        boolean result = jobService.cancelJob(jobSchedulerReq.getJobName());
        String msg = result ? "任务禁用成功" : "任务禁用失败";
        resp.setMessage(msg);
        resp.setSuccess(result);
        return resp;
    }

    @PatchMapping("/reset")
    public CommonResp resetJobByName(JobSchedulerReq jobSchedulerReq) {
        CommonResp resp = new CommonResp<>();
        boolean result = jobService.resetJob(jobSchedulerReq.getJobName());
        String msg = result ? "任务重启成功" : "任务重启失败";
        resp.setMessage(msg);
        resp.setSuccess(result);
        return resp;
    }

    @PostMapping("/add")
    public CommonResp addJob(JobSchedulerReq jobSchedulerReq) {
        CommonResp resp = new CommonResp<>();
        jobSchedulerService.addJob(jobSchedulerReq);
        return resp;
    }

    @DeleteMapping("/delete")
    public CommonResp deleteJob(JobSchedulerReq jobSchedulerReq) {
        CommonResp resp = new CommonResp<>();
        jobSchedulerService.deleteByJobName(jobSchedulerReq.getJobName());
        return resp;
    }
}
