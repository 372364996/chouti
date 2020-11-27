package com.hanzhi.chouti.bean;

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

    public String getDateTimeStr() {
        return DateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        DateTimeStr = dateTimeStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teacherId);
        dest.writeInt(this.classId);
        dest.writeString(this.DateTimeStr);
    }

    public ClassApplyBean() {
    }

    protected ClassApplyBean(Parcel in) {
        this.teacherId = in.readInt();
        this.classId = in.readInt();
        this.DateTimeStr = in.readString();
    }

    public static final Parcelable.Creator<ClassApplyBean> CREATOR = new Parcelable.Creator<ClassApplyBean>() {
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
