package com.avalon.wiki.service.impl;

import com.alibaba.excel.EasyExcel;
import com.avalon.wiki.excel.entity.Demo;
import com.avalon.wiki.excel.listener.DemoListener;
import com.avalon.wiki.service.iface.IExcelTestService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelTestService implements IExcelTestService {

    @Override
    public void read() {
        String fileName = "C:\\Users\\Administrator\\Desktop\\test.xlsx";
        EasyExcel.read(fileName, Demo.class, new DemoListener()).sheet().doRead();
    }

    @Override
    public void write() {
        String fileName = "C:\\Users\\Administrator\\Desktop\\test.xlsx";
        Demo demo = new Demo();
        demo.setNum(4);
        demo.setName("阿瓦隆");
        demo.setDate(new Date());
        List<Demo> list = new ArrayList<>();
        list.add(demo);
        EasyExcel.write(fileName, Demo.class).sheet(1).doWrite(list);
    }

    public static void main(String[] args) {
        ExcelTestService excelTestService = new ExcelTestService();
        excelTestService.write();
        excelTestService.read();
    }
}
