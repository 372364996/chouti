package com.hanzhi.onlineclassroom.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 18:27
 */
public class ClassApplyBean implements Parcelable {
    private int teacherId;
    private int classId;
    private String teacherName;
    private String className;
    private String DateTimeStr;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDateTimeStr() {
        return DateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        DateTimeStr = dateTimeStr;
    }

    public ClassApplyBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teacherId);
        dest.writeInt(this.classId);
        dest.writeString(this.teacherName);
        dest.writeString(this.className);
        dest.writeString(this.DateTimeStr);
    }

    protected ClassApplyBean(Parcel in) {
        this.teacherId = in.readInt();
        this.classId = in.readInt();
        this.teacherName = in.readString();
        this.className = in.readString();
        this.DateTimeStr = in.readString();
    }

    public static final Creator<ClassApplyBean> CREATOR = new Creator<ClassApplyBean>() {
        @Override
        public ClassApplyBean createFromParcel(Parcel source) {
            return new ClassApplyBean(source);
        }

        @Override
        public ClassApplyBean[] newArray(int size) {
            return new ClassApplyBean[size];
        }
    };
}
