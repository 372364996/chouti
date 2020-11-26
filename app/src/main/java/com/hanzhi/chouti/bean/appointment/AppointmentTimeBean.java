package com.hanzhi.chouti.bean.appointment;

import android.os.Parcel;
import android.os.Parcelable;

import com.chewawa.baselibrary.base.BaseCheckRecycleViewAdapter;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:00
 */
public class AppointmentTimeBean implements BaseCheckRecycleViewAdapter.CheckItem, Parcelable {


    /**
     * Id : 1
     * TeacherId : 8
     * ClassTime : /Date(1433322755000)/
     * CreateTime : /Date(1522105495567)/
     */

    private boolean IsCanUse;
    private String Time;
    private String Date;
    private boolean isChecked;

    public boolean getIsCanUse() {
        return IsCanUse;
    }

    public void setIsCanUse(boolean isCanUse) {
        IsCanUse = isCanUse;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public int getItemType() {
        return 0;
    }


    public AppointmentTimeBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.IsCanUse ? (byte) 1 : (byte) 0);
        dest.writeString(this.Time);
        dest.writeString(this.Date);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected AppointmentTimeBean(Parcel in) {
        this.IsCanUse = in.readByte() != 0;
        this.Time = in.readString();
        this.Date = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<AppointmentTimeBean> CREATOR = new Creator<AppointmentTimeBean>() {
        @Override
        public AppointmentTimeBean createFromParcel(Parcel source) {
            return new AppointmentTimeBean(source);
        }

        @Override
        public AppointmentTimeBean[] newArray(int size) {
            return new AppointmentTimeBean[size];
        }
    };
}
