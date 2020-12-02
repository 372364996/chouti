package com.hanzhi.chouti.bean.teachers;

import android.os.Parcel;
import android.os.Parcelable;

public class AppraiseBean implements Parcelable {

    /**
     * Id : 6103
     * Message : null
     * UserId : 1050
     * ClassId : 1057
     * ClassTime : 2019/2/20 10:30:00
     * CreateTime : 2019/2/20 10:03:30
     * IsPayed : false
     * Mobile : null
     * PayNumber : null
     * Number : CO20190220100330120096
     * PayTime :
     * Status : 1
     * Content : 只能给你2分哦
     * Scores : 2
     * VerifyTime : 2019/2/20 11:55:10
     * TeacherId : 1045
     * HeadImg : /Upload/HeadImg/eb4cbe02-f929-4d94-95e7-98f530aed9ae.jpg
     * WeiChat : null
     * UserName : xiaopppp
     * ClassName : 2-2 企尖澜(げ,こ,そ,ぇ,ぜ,え) ver.2.0
     * ClassType : 0
     * Level : 0
     */

    private int Id;
    private String Message;
    private int UserId;
    private int ClassId;
    private String ClassTime;
    private String CreateTime;
    private boolean IsPayed;
    private String Mobile;
    private String PayNumber;
    private String Number;
    private String PayTime;
    private int Status;
    private String Content;
    private int Scores;
    private String VerifyTime;
    private int TeacherId;
    private String HeadImg;
    private String WeiChat;
    private String UserName;
    private String ClassName;
    private int ClassType;
    private int Level;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        ClassId = classId;
    }

    public String getClassTime() {
        return ClassTime;
    }

    public void setClassTime(String classTime) {
        ClassTime = classTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public boolean isPayed() {
        return IsPayed;
    }

    public void setPayed(boolean payed) {
        IsPayed = payed;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPayNumber() {
        return PayNumber;
    }

    public void setPayNumber(String payNumber) {
        PayNumber = payNumber;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getScores() {
        return Scores;
    }

    public void setScores(int scores) {
        Scores = scores;
    }

    public String getVerifyTime() {
        return VerifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        VerifyTime = verifyTime;
    }

    public int getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(int teacherId) {
        TeacherId = teacherId;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String headImg) {
        HeadImg = headImg;
    }

    public String getWeiChat() {
        return WeiChat;
    }

    public void setWeiChat(String weiChat) {
        WeiChat = weiChat;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getClassType() {
        return ClassType;
    }

    public void setClassType(int classType) {
        ClassType = classType;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Message);
        dest.writeInt(this.UserId);
        dest.writeInt(this.ClassId);
        dest.writeString(this.ClassTime);
        dest.writeString(this.CreateTime);
        dest.writeByte(this.IsPayed ? (byte) 1 : (byte) 0);
        dest.writeString(this.Mobile);
        dest.writeString(this.PayNumber);
        dest.writeString(this.Number);
        dest.writeString(this.PayTime);
        dest.writeInt(this.Status);
        dest.writeString(this.Content);
        dest.writeInt(this.Scores);
        dest.writeString(this.VerifyTime);
        dest.writeInt(this.TeacherId);
        dest.writeString(this.HeadImg);
        dest.writeString(this.WeiChat);
        dest.writeString(this.UserName);
        dest.writeString(this.ClassName);
        dest.writeInt(this.ClassType);
        dest.writeInt(this.Level);
    }

    public AppraiseBean() {
    }

    protected AppraiseBean(Parcel in) {
        this.Id = in.readInt();
        this.Message = in.readString();
        this.UserId = in.readInt();
        this.ClassId = in.readInt();
        this.ClassTime = in.readString();
        this.CreateTime = in.readString();
        this.IsPayed = in.readByte() != 0;
        this.Mobile = in.readString();
        this.PayNumber = in.readString();
        this.Number = in.readString();
        this.PayTime = in.readString();
        this.Status = in.readInt();
        this.Content = in.readString();
        this.Scores = in.readInt();
        this.VerifyTime = in.readString();
        this.TeacherId = in.readInt();
        this.HeadImg = in.readString();
        this.WeiChat = in.readString();
        this.UserName = in.readString();
        this.ClassName = in.readString();
        this.ClassType = in.readInt();
        this.Level = in.readInt();
    }

    public static final Parcelable.Creator<AppraiseBean> CREATOR = new Parcelable.Creator<AppraiseBean>() {
        @Override
        public AppraiseBean createFromParcel(Parcel source) {
            return new AppraiseBean(source);
        }

        @Override
        public AppraiseBean[] newArray(int size) {
            return new AppraiseBean[size];
        }
    };
}
