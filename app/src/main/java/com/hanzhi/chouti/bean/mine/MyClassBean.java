package com.hanzhi.chouti.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

public class MyClassBean implements Parcelable {

    /**
     * Id : 7023
     * Message : null
     * UserId : 2
     * ClassId : 1376
     * ClassTime : 2020-09-15 17:30:00
     * CreateTime : 2020-09-10 15:03:28
     * UserFirstIntoClassTime :
     * TeacherFirstIntoClassTime :
     * AttendanceStatus : 0
     * IsPayed : false
     * Mobile : null
     * PayNumber : null
     * Number : CO20200909200328666649
     * PayTime :
     * Status : 2
     * VerifyTime :
     * TeacherId : 1045
     * HeadImg : /Upload/HeadImg/1bf62ad8-23a5-42cf-96ad-50a670242f24.jpg
     * WeiChat : null
     * TeacherName : 강은석
     * IsExpire : false
     * MovieUrl :
     * ClassName : 学校生活2
     * ClassType : 2
     * ClassTypeName : 初级
     * TeacherLevel : 5
     */

    private int Id;
    private String Message;
    private int UserId;
    private int ClassId;
    private String ClassTime;
    private String CreateTime;
    private String UserFirstIntoClassTime;
    private String TeacherFirstIntoClassTime;
    private int AttendanceStatus;
    private boolean IsPayed;
    private String Mobile;
    private String PayNumber;
    private String Number;
    private String PayTime;
    private int Status;
    private String VerifyTime;
    private int TeacherId;
    private String HeadImg;
    private String WeiChat;
    private String TeacherName;
    private boolean IsExpire;
    private String MovieUrl;
    private String ClassName;
    private int ClassType;
    private String ClassTypeName;
    private int TeacherLevel;
    private String StartAndEndTime;
    private String OrderStatusDescription;

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

    public String getUserFirstIntoClassTime() {
        return UserFirstIntoClassTime;
    }

    public void setUserFirstIntoClassTime(String userFirstIntoClassTime) {
        UserFirstIntoClassTime = userFirstIntoClassTime;
    }

    public String getTeacherFirstIntoClassTime() {
        return TeacherFirstIntoClassTime;
    }

    public void setTeacherFirstIntoClassTime(String teacherFirstIntoClassTime) {
        TeacherFirstIntoClassTime = teacherFirstIntoClassTime;
    }

    public int getAttendanceStatus() {
        return AttendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        AttendanceStatus = attendanceStatus;
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

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public boolean isExpire() {
        return IsExpire;
    }

    public void setExpire(boolean expire) {
        IsExpire = expire;
    }

    public String getMovieUrl() {
        return MovieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        MovieUrl = movieUrl;
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

    public String getClassTypeName() {
        return ClassTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        ClassTypeName = classTypeName;
    }

    public int getTeacherLevel() {
        return TeacherLevel;
    }

    public void setTeacherLevel(int teacherLevel) {
        TeacherLevel = teacherLevel;
    }

    public String getStartAndEndTime() {
        return StartAndEndTime;
    }

    public void setStartAndEndTime(String startAndEndTime) {
        StartAndEndTime = startAndEndTime;
    }

    public String getOrderStatusDescription() {
        return OrderStatusDescription;
    }

    public void setOrderStatusDescription(String orderStatusDescription) {
        OrderStatusDescription = orderStatusDescription;
    }

    public MyClassBean() {
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
        dest.writeString(this.UserFirstIntoClassTime);
        dest.writeString(this.TeacherFirstIntoClassTime);
        dest.writeInt(this.AttendanceStatus);
        dest.writeByte(this.IsPayed ? (byte) 1 : (byte) 0);
        dest.writeString(this.Mobile);
        dest.writeString(this.PayNumber);
        dest.writeString(this.Number);
        dest.writeString(this.PayTime);
        dest.writeInt(this.Status);
        dest.writeString(this.VerifyTime);
        dest.writeInt(this.TeacherId);
        dest.writeString(this.HeadImg);
        dest.writeString(this.WeiChat);
        dest.writeString(this.TeacherName);
        dest.writeByte(this.IsExpire ? (byte) 1 : (byte) 0);
        dest.writeString(this.MovieUrl);
        dest.writeString(this.ClassName);
        dest.writeInt(this.ClassType);
        dest.writeString(this.ClassTypeName);
        dest.writeInt(this.TeacherLevel);
        dest.writeString(this.StartAndEndTime);
        dest.writeString(this.OrderStatusDescription);
    }

    protected MyClassBean(Parcel in) {
        this.Id = in.readInt();
        this.Message = in.readString();
        this.UserId = in.readInt();
        this.ClassId = in.readInt();
        this.ClassTime = in.readString();
        this.CreateTime = in.readString();
        this.UserFirstIntoClassTime = in.readString();
        this.TeacherFirstIntoClassTime = in.readString();
        this.AttendanceStatus = in.readInt();
        this.IsPayed = in.readByte() != 0;
        this.Mobile = in.readString();
        this.PayNumber = in.readString();
        this.Number = in.readString();
        this.PayTime = in.readString();
        this.Status = in.readInt();
        this.VerifyTime = in.readString();
        this.TeacherId = in.readInt();
        this.HeadImg = in.readString();
        this.WeiChat = in.readString();
        this.TeacherName = in.readString();
        this.IsExpire = in.readByte() != 0;
        this.MovieUrl = in.readString();
        this.ClassName = in.readString();
        this.ClassType = in.readInt();
        this.ClassTypeName = in.readString();
        this.TeacherLevel = in.readInt();
        this.StartAndEndTime = in.readString();
        this.OrderStatusDescription = in.readString();
    }

    public static final Creator<MyClassBean> CREATOR = new Creator<MyClassBean>() {
        @Override
        public MyClassBean createFromParcel(Parcel source) {
            return new MyClassBean(source);
        }

        @Override
        public MyClassBean[] newArray(int size) {
            return new MyClassBean[size];
        }
    };
}
