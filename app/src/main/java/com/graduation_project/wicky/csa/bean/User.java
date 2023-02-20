package com.graduation_project.wicky.csa.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    //用户id
    private int id; //id
    //用户密码
    private String password; //password
    //用户名
    private String userName; //username
    //身份
    private boolean isAdmin; //userType(String)

    private String phone;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    protected User(Parcel in) {
        id = in.readInt();
        password = in.readString();
        userName = in.readString();
        isAdmin = in.readByte() != 0;
        phone= in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(password);
        parcel.writeString(userName);
        parcel.writeByte((byte) (isAdmin ? 1 : 0));
        parcel.writeString(phone);
    }
}
