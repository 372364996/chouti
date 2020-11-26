package com.hanzhi.chouti.bean.teachers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 11:00
 */
public class TeacherBean implements Parcelable {

    /**
     * Id : 8
     * CreateTime : /Date(1522023068890)/
     * Description : 李四
     * HeadImg :
     * TeacherLevel : 0
     * Name : 张三
     * Tags : 风趣幽默
     * UserId : 8
     */

    private int Id;
    private String CreateTime;
    private String Description;
    private String HeadImg;
    private int TeacherLevel;
    private String Name;
    private String Tags;
    private String AvgScore;
    private int UserId;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String HeadImg) {
        this.HeadImg = HeadImg;
    }

    public int getTeacherLevel() {
        return TeacherLevel;
    }

    public void setTeacherLevel(int TeacherLevel) {
        this.TeacherLevel = TeacherLevel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String Tags) {
        this.Tags = Tags;
    }

    public String getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(String avgScore) {
        AvgScore = avgScore;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public TeacherBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.CreateTime);
        dest.writeString(this.Description);
        dest.writeString(this.HeadImg);
        dest.writeInt(this.TeacherLevel);
        dest.writeString(this.Name);
        dest.writeString(this.Tags);
        dest.writeString(this.AvgScore);
        dest.writeInt(this.UserId);
    }

    protected TeacherBean(Parcel in) {
        this.Id = in.readInt();
        this.CreateTime = in.readString();
        this.Description = in.readString();
        this.HeadImg = in.readString();
        this.TeacherLevel = in.readInt();
        this.Name = in.readString();
        this.Tags = in.readString();
        this.AvgScore = in.readString();
        this.UserId = in.readInt();
    }

    public static final Creator<TeacherBean> CREATOR = new Creator<TeacherBean>() {
        @Override
        public TeacherBean createFromParcel(Parcel source) {
            return new TeacherBean(source);
        }

        @Override
        public TeacherBean[] newArray(int size) {
            return new TeacherBean[size];
        }
    };
}
