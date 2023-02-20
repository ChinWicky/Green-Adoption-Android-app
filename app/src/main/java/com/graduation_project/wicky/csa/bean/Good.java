package com.graduation_project.wicky.csa.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public  class Good implements Parcelable{
    public static final Creator<Good> CREATOR = new Creator<Good>() {
        @Override
        public Good createFromParcel(Parcel in) {
            return new Good(in);
        }

        @Override
        public Good[] newArray(int size) {
            return new Good[size];
        }
    };
    private int id;  //id
    private String name; //productName
    private double price;  //unitPrice(int)
    //种植业1 畜牧业2 渔业3
    private int category;
    //商品图片集
    private List<String> goodsImgList; //picture
    private int supplierId; //producerId
    private int inventory;  //inventory
    private String placeId;
    private String description; //remark

    public Good(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Good(String name, double price, int category) {
        this.name = name;
        this.price = price;
        this.category = category;
        //this.date = new Date();
    }

    protected Good(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        category = in.readInt();
        goodsImgList = in.createStringArrayList();
        supplierId = in.readInt();
        inventory = in.readInt();
        placeId = in.readString();
        description = in.readString();
    }

    public Good() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public List<String> getGoodsImgList() {
        return goodsImgList;
    }

    public void setGoodsImgList(List<String> goodsImgList) {
        this.goodsImgList = goodsImgList;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeInt(category);
        parcel.writeStringList(goodsImgList);
        parcel.writeInt(supplierId);
        parcel.writeInt(inventory);
        parcel.writeString(placeId);
        parcel.writeString(description);
    }
}
