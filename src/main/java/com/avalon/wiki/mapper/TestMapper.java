package com.avalon.wiki.mapper;

import com.avalon.wiki.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Test> list();
}
