package com.hanzhi.chouti.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassCardBean implements Parcelable {

    /**
     * Id : 261
     * Name : 60次卡
     * CreateTime : 2020/03/23 16:34:26
     * Expires : 9999/12/31 23:59:59
     * Counts : 60
     * UseCounts : 0
     * Number : CCO20200323163426158124
     * Status : 已支付
     * UserId : 2
     * Description :
     * Tip : 请联系助教或自行进行约课
     */

    private int Id;
    private String Name;
    private String CreateTime;
    private String Expires;
    private int Counts;
    private int UseCounts;
    private String Number;
    private String Status;
    private int UserId;
    private String Description;
    private String Tip;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getExpires() {
        return Expires;
    }

    public void setExpires(String Expires) {
        this.Expires = Expires;
    }

    public int getCounts() {
        return Counts;
    }

    public void setCounts(int Counts) {
        this.Counts = Counts;
    }

    public int getUseCounts() {
        return UseCounts;
    }

    public void setUseCounts(int UseCounts) {
        this.UseCounts = UseCounts;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String Tip) {
        this.Tip = Tip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.CreateTime);
        dest.writeString(this.Expires);
        dest.writeInt(this.Counts);
        dest.writeInt(this.UseCounts);
        dest.writeString(this.Number);
        dest.writeString(this.Status);
        dest.writeInt(this.UserId);
        dest.writeString(this.Description);
        dest.writeString(this.Tip);
    }

    public ClassCardBean() {
    }

    protected ClassCardBean(Parcel in) {
        this.Id = in.readInt();
        this.Name = in.readString();
        this.CreateTime = in.readString();
        this.Expires = in.readString();
        this.Counts = in.readInt();
        this.UseCounts = in.readInt();
        this.Number = in.readString();
        this.Status = in.readString();
        this.UserId = in.readInt();
        this.Description = in.readString();
        this.Tip = in.readString();
    }

    public static final Parcelable.Creator<ClassCardBean> CREATOR = new Parcelable.Creator<ClassCardBean>() {
        @Override
        public ClassCardBean createFromParcel(Parcel source) {
            return new ClassCardBean(source);
        }

        @Override
        public ClassCardBean[] newArray(int size) {
            return new ClassCardBean[size];
        }
    };
}
