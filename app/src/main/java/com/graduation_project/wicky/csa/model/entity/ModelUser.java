package com.graduation_project.wicky.csa.model.entity;

/**
 * Created by Wicky on 2019/2/26.
 */

public class ModelUser {


    /**
     * available : 1
     * createTime : 2019-03-06 21:53:57
     * email : 101
     * fullName : 102
     * id : 4
     * lastModified : 2019-03-24 16:40:05
     * password : d06c68605c32279eb44ec71aee8e1fbf
     * phoneNumber : 102
     * userNumber : 8268218271
     * userType : PRODUCER
     * username : 101
     */

    private int available = 1;
    private String createTime;
    private String email;
    private String fullName;
    private int id;
    private String lastModified;
    private String password;
    private String phoneNumber;
    private String userNumber;
    private String userType = "PRODUCER";
    private String username;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
