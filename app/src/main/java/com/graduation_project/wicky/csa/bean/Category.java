package com.graduation_project.wicky.csa.bean;


public class Category {
    private int id;
    //种植业  畜牧业  渔业
    private String name;
    //单位（亩、只、千克）
    private String unit;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
