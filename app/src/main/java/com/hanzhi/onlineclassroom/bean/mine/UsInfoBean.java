package com.hanzhi.onlineclassroom.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/12/4 14:05
 */
public class UsInfoBean implements Parcelable {

    /**
     * WeiXin1 : ceck-hanz
     * WeiXin2 : hanzhilove23
     * Tel : 010-2791-1641
     * OfficialWebsite : www.hanzhistudy.com
     */

    private String WeiXin1;
    private String WeiXin2;
    private String Tel;
    private String OfficialWebsite;

    public String getWeiXin1() {
        return WeiXin1;
    }

    public void setWeiXin1(String WeiXin1) {
        this.WeiXin1 = WeiXin1;
    }

    public String getWeiXin2() {
        return WeiXin2;
    }

    public void setWeiXin2(String WeiXin2) {
        this.WeiXin2 = WeiXin2;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getOfficialWebsite() {
        return OfficialWebsite;
    }

    public void setOfficialWebsite(String OfficialWebsite) {
        this.OfficialWebsite = OfficialWebsite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.WeiXin1);
        dest.writeString(this.WeiXin2);
        dest.writeString(this.Tel);
        dest.writeString(this.OfficialWebsite);
    }

    public UsInfoBean() {
    }

    protected UsInfoBean(Parcel in) {
        this.WeiXin1 = in.readString();
        this.WeiXin2 = in.readString();
        this.Tel = in.readString();
        this.OfficialWebsite = in.readString();
    }

    public static final Parcelable.Creator<UsInfoBean> CREATOR = new Parcelable.Creator<UsInfoBean>() {
        @Override
        public UsInfoBean createFromParcel(Parcel source) {
            return new UsInfoBean(source);
        }

        @Override
        public UsInfoBean[] newArray(int size) {
            return new UsInfoBean[size];
        }
    };
}
