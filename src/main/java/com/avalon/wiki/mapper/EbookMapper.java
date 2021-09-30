package com.avalon.wiki.mapper;

import com.avalon.wiki.domain.Ebook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbookMapper {
    List<Ebook> findAll();
    List<Ebook> findByName(String bookName);
}