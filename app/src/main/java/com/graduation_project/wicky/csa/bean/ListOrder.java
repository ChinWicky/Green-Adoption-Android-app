package com.graduation_project.wicky.csa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Wicky on 2019/2/26.
 */

public class ListOrder {


    //创建时间
    private long createDate; //orderDate
    //订单id
    private int id; //id
    //金额
    private double sum; //totalPrice
    //商品总金额
    private double goodTotal;//unitPrice*amount
    //商品清单
    private List<Good> goodList; //adoptOrderItem
    // 数量
    private int amount; //orderAmount
    //备注
    private String note; //remark
    //状态
    private int orderStatus; //orderStatus(String)-------
    //订单号
    private String orderNumber;    //orderNumber
    //合约期限
    private int year;
    //供应商id
    private int supplerId; //producerId
    //用户id
    private int adopterId; //buyerId
    //是否有保险
    private boolean isInsure;

    public ListOrder() {

    }
}
