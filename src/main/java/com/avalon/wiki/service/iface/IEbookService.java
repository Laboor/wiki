package com.avalon.wiki.service.iface;

import com.avalon.wiki.request.EbookReq;
import com.avalon.wiki.response.EbookResp;

import java.util.List;

public interface IEbookService {
    public List<EbookResp> list(EbookReq req);
}
