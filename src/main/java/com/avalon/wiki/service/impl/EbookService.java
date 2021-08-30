package com.avalon.wiki.service.impl;

import com.avalon.wiki.domain.Ebook;
import com.avalon.wiki.domain.EbookExample;
import com.avalon.wiki.mapper.EbookMapper;
import com.avalon.wiki.request.EbookReq;
import com.avalon.wiki.response.EbookResp;
import com.avalon.wiki.service.iface.IEbookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService implements IEbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp); // 拷贝对象属性
            respList.add(ebookResp);
        }

        return  respList;
    }
}
