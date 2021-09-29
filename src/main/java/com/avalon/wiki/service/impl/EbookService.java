package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.Ebook;
import com.avalon.wiki.mapper.EbookMapper;
import com.avalon.wiki.request.EbookReq;
import com.avalon.wiki.response.EbookResp;
import com.avalon.wiki.service.iface.IEbookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService implements IEbookService {

    @Resource
    private EbookMapper ebookMapper;

    @Override
    public List<EbookResp> list(EbookReq req) {
        List<Ebook> ebookList = ebookMapper.findByName(req.getName());
        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp); // 拷贝对象属性
            respList.add(ebookResp);
        }

        return respList;
    }
}
