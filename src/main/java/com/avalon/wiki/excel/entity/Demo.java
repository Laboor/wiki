package com.avalon.wiki.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class Demo {
    @ExcelProperty(index = 0)
    private int num;
    @ExcelProperty(index = 1)
    private String name;
    @ExcelProperty(index = 2)
    private Date date;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
