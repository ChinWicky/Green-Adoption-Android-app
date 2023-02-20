package com.graduation_project.wicky.csa.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Wicky on 2019/2/26.
 */

public class Order implements Parcelable {

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
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

    public Order() {

    }


    public Order(long createDate, double sum, double goodTotal, List<Good> goodList, int amount, String remark, int orderStatus, int year, int supplerId, int adopterId, boolean isInsure) {
        this.createDate = createDate;
        this.sum = sum;
        this.goodTotal = goodTotal;
        this.goodList = goodList;
        this.amount = amount;
        this.note = remark;
        this.orderStatus = orderStatus;
        this.year = year;
        this.supplerId = supplerId;
        this.adopterId = adopterId;
        this.isInsure = isInsure;
    }

    protected Order(Parcel in) {
        createDate = in.readLong();
        id = in.readInt();
        sum = in.readDouble();
        goodTotal = in.readDouble();
        goodList = in.createTypedArrayList(Good.CREATOR);
        amount = in.readInt();
        note = in.readString();
        orderStatus = in.readInt();
        orderNumber = in.readString();
        year = in.readInt();
        supplerId = in.readInt();
        adopterId = in.readInt();
        isInsure = in.readByte() != 0;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public List<Good> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public double getGoodTotal() {
        return goodTotal;
    }

    public void setGoodTotal(double goodTotal) {
        this.goodTotal = goodTotal;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(int supplerId) {
        this.supplerId = supplerId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }

    public boolean isInsure() {
        return isInsure;
    }

    public void setInsure(boolean insure) {
        isInsure = insure;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(createDate);
        parcel.writeInt(id);
        parcel.writeDouble(sum);
        parcel.writeDouble(goodTotal);
        parcel.writeTypedList(goodList);
        parcel.writeInt(amount);
        parcel.writeString(note);
        parcel.writeInt(orderStatus);
        parcel.writeString(orderNumber);
        parcel.writeInt(year);
        parcel.writeInt(supplerId);
        parcel.writeInt(adopterId);
        parcel.writeByte((byte) (isInsure ? 1 : 0));
    }
}
