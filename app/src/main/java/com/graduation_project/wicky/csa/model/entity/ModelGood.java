package com.graduation_project.wicky.csa.model.entity;


import java.util.List;

public class ModelGood {


    /**
     * available : 1
     * brand : 111
     * createTime : 2019-03-23 19:26:52
     * cycleDate : 111
     * extJson :
     * id : 1
     * inventory : 111
     * lastModified : 2019-03-23 19:26:52
     * picture : ["/picture/ad5f53a4-01f4-476e-b7c8-04d8e653d355.jpg"]
     * producerId : 5
     * productName : 111
     * productNumber : 1992964846
     * remark : 111
     * sourceName :
     * unit :
     * unitPrice : 111
     * vedioUrl : rtmp://jiayou.ante3.com/zhibo/3321?auth_key=1706061534-0-0-1c8fc3a5d1cd05a2d0413c9e68bec991
     */

    private int available;
    private String brand;
    private String createTime;
    private String cycleDate;
    private String extJson;
    private int id;
    private int inventory;
    private String lastModified;
    private int producerId;
    private String productName;
    private String productNumber;
    private String remark;
    private String sourceName;
    private String unit;
    private int unitPrice;
    private String vedioUrl;
    private List<String> picture;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCycleDate() {
        return cycleDate;
    }

    public void setCycleDate(String cycleDate) {
        this.cycleDate = cycleDate;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
