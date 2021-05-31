package com.hanzhi.onlineclassroom.bean.appointment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:41
 */
public class AppointmentTabBean implements Parcelable {

    /**
     * date : 2020-11-26
     * week : 周四
     */

    private String date;
    private String week;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.week);
    }

    public AppointmentTabBean() {
    }

    protected AppointmentTabBean(Parcel in) {
        this.date = in.readString();
        this.week = in.readString();
    }

    public static final Parcelable.Creator<AppointmentTabBean> CREATOR = new Parcelable.Creator<AppointmentTabBean>() {
        @Override
        public AppointmentTabBean createFromParcel(Parcel source) {
            return new AppointmentTabBean(source);
        }

        @Override
        public AppointmentTabBean[] newArray(int size) {
            return new AppointmentTabBean[size];
        }
    };
}
