package com.hanzhi.chouti.bean.selectclass;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassBean implements Parcelable {

    /**
     * Id : 1473
     * ClassGuid : 6bfc807d-74b9-4b3f-85e7-32edb6315b22
     * Img : /Upload/img/a1d8ef1e-e8d0-4a0f-8625-65d334be5c45.jpg
     * Name : 20. 语调, 问候语
     * CreateTime : 6/24/2020 3:06:30 PM
     * Description : null
     * Price : 120.0
     * IsAudition : false
     * ClassType : 6
     * StartTime : 6/24/2020 3:06:30 PM
     */

    private int Id;
    private String ClassGuid;
    private String Img;
    private String Name;
    private String CreateTime;
    private String Description;
    private double Price;
    private boolean IsAudition;
    private int ClassType;
    private String StartTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getClassGuid() {
        return ClassGuid;
    }

    public void setClassGuid(String classGuid) {
        ClassGuid = classGuid;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean isAudition() {
        return IsAudition;
    }

    public void setAudition(boolean audition) {
        IsAudition = audition;
    }

    public int getClassType() {
        return ClassType;
    }

    public void setClassType(int classType) {
        ClassType = classType;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.ClassGuid);
        dest.writeString(this.Img);
        dest.writeString(this.Name);
        dest.writeString(this.CreateTime);
        dest.writeString(this.Description);
        dest.writeDouble(this.Price);
        dest.writeByte(this.IsAudition ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ClassType);
        dest.writeString(this.StartTime);
    }

    public ClassBean() {
    }

    protected ClassBean(Parcel in) {
        this.Id = in.readInt();
        this.ClassGuid = in.readString();
        this.Img = in.readString();
        this.Name = in.readString();
        this.CreateTime = in.readString();
        this.Description = in.readString();
        this.Price = in.readDouble();
        this.IsAudition = in.readByte() != 0;
        this.ClassType = in.readInt();
        this.StartTime = in.readString();
    }

    public static final Parcelable.Creator<ClassBean> CREATOR = new Parcelable.Creator<ClassBean>() {
        @Override
        public ClassBean createFromParcel(Parcel source) {
            return new ClassBean(source);
        }

        @Override
        public ClassBean[] newArray(int size) {
            return new ClassBean[size];
        }
    };
}
