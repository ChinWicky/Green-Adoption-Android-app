package com.graduation_project.wicky.csa.bean;

/**
 * Created by Wicky on 2019/2/26.
 */
//收件信息
public class Consignee {
    //用户
    private User user;
    //收货人姓名
    private String consigneeName;
    //收货人手机号
    private String phoneNumber;
    //收货地址
    private String consigneeAdress;
    //邮政编码
    private String postalCode;

    public  User   getUser() {
        return user;
    }public void   setUser(User user) {
        this.user = user;
    }public String getConsigneeName() {
        return consigneeName;
    }public void   setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }public String getPhoneNumber() {
        return phoneNumber;
    }public void   setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }public String getConsigneeAdress() {
        return consigneeAdress;
    }public void   setConsigneeAdress(String consigneeAdress) {
        this.consigneeAdress = consigneeAdress;
    }public String getPostalCode() {
        return postalCode;
    }public void   setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }



}
