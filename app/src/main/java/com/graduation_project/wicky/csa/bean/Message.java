package com.graduation_project.wicky.csa.bean;


public class Message {

    private int id;
    private String orderNumber;
    private int senderId;
    private int receiverId;
    private String content;
    private int messageStatus;


    public Message() {

    }

    public Message(String orderNumber, int senderId, int receiverId, String content) {
        this.orderNumber = orderNumber;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

}
