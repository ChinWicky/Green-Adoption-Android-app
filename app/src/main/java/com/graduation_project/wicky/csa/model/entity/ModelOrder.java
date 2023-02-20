package com.graduation_project.wicky.csa.model.entity;

import java.util.List;

/**
 * Created by Wicky on 2019/2/26.
 */

public class ModelOrder {


    /**
     * address : {"address":"123456","phoneNumber":"123456","receiver":"linlin"}
     * adoptOrderItem : [{"available":1,"brand":"111","createTime":"2019-03-23 19:33:27","id":1,"itemId":1,"itemName":"111","itemNumber":"1992964846","itemPrice":111,"itemType":"","lastModified":"2019-03-23 19:33:27","orderAmount":1,"orderId":1,"orderNumber":"3673405472","unit":""}]
     * available : 1
     * buyerId : 1
     * buyerName : 101
     * buyerNumber : 0812198633
     * createTime : 2019-03-23 19:33:27
     * id : 1
     * lastModified : 2019-03-23 19:33:27
     * orderAmount : 1
     * orderDate : 2019-03-23 19:33:27
     * orderNumber : 3673405472
     * orderStatus : IN_PROGRESS
     * paidPrice : 0
     * paymentStatus : NO_PAY
     * producerId : 0
     * remark :
     * totalPrice : 111
     */

    private AddressBean address;
    private int available;
    private int buyerId;
    private String buyerName;
    private String buyerNumber;
    private String createTime;
    private int id;
    private String lastModified;
    private int orderAmount;
    private String orderDate;
    private String orderNumber;
    private String orderStatus;
    private int paidPrice;
    private String paymentStatus;
    private int producerId;
    private String remark;
    private int totalPrice;
    private List<AdoptOrderItemBean> adoptOrderItem;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(int paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<AdoptOrderItemBean> getAdoptOrderItem() {
        return adoptOrderItem;
    }

    public void setAdoptOrderItem(List<AdoptOrderItemBean> adoptOrderItem) {
        this.adoptOrderItem = adoptOrderItem;
    }

    public static class AddressBean {
        /**
         * address : 123456
         * phoneNumber : 123456
         * receiver : linlin
         */

        private String address;
        private String phoneNumber;
        private String receiver;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }
    }

    public static class AdoptOrderItemBean {
        /**
         * available : 1
         * brand : 111
         * createTime : 2019-03-23 19:33:27
         * id : 1
         * itemId : 1
         * itemName : 111
         * itemNumber : 1992964846
         * itemPrice : 111
         * itemType :
         * lastModified : 2019-03-23 19:33:27
         * orderAmount : 1
         * orderId : 1
         * orderNumber : 3673405472
         * unit :
         */

        private int available;
        private String brand;
        private String createTime;
        private int id;
        private int itemId;
        private String itemName;
        private String itemNumber;
        private int itemPrice;
        private String itemType;
        private String lastModified;
        private int orderAmount;
        private int orderId;
        private String orderNumber;
        private String unit;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(String itemNumber) {
            this.itemNumber = itemNumber;
        }

        public int getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public int getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(int orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
