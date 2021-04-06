package com.hanzhi.chouti.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {

    /**
     * success : true
     * status : 0
     * token : a81c1cac-7c6d-4d5f-94cd-8f5a980f461d
     * Id : 2
     * HeadImg : http://hanzhiapp.hdlebaobao.cn//Upload/HeadImg/0e1b13cb-9030-4f83-a345-754b3a8136e6.jpg
     * imtoken : null
     * Mobile : 15822771484
     * isTeacher : false
     * teacherid : 0
     */

    private boolean success;
    private int status;
    private String token;
    private int Id;
    private String HeadImg;
    private String imtoken;
    private String Mobile;
    private boolean isTeacher;
    private int teacherid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String HeadImg) {
        this.HeadImg = HeadImg;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public boolean isIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeString(this.token);
        dest.writeInt(this.Id);
        dest.writeString(this.HeadImg);
        dest.writeString(this.imtoken);
        dest.writeString(this.Mobile);
        dest.writeByte(this.isTeacher ? (byte) 1 : (byte) 0);
        dest.writeInt(this.teacherid);
        dest.writeString(this.userName);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.status = in.readInt();
        this.token = in.readString();
        this.Id = in.readInt();
        this.HeadImg = in.readString();
        this.imtoken = in.readString();
        this.Mobile = in.readString();
        this.userName = in.readString();
        this.isTeacher = in.readByte() != 0;
        this.teacherid = in.readInt();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
