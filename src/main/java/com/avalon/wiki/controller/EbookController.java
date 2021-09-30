package com.avalon.wiki.controller;

import com.avalon.wiki.request.EbookReq;
import com.avalon.wiki.response.CommonResp;
import com.avalon.wiki.response.EbookResp;
import com.avalon.wiki.service.iface.IEbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private IEbookService ebookService;

    @GetMapping("/list")
    public CommonResp<List<EbookResp>> list(EbookReq req) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setData(list);
        resp.setMessage("成功");
        return resp;
    }
}
