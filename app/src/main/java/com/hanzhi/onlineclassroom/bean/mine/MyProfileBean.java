package com.hanzhi.onlineclassroom.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;


public class MyProfileBean implements Parcelable {


    /**
     * Id : 2
     * NickName : dupe
     * Mobile : 15822771484
     * Level : 1
     * HeadImg : null
     * SexName : 男
     * IsBoughtAuditionCardOrder : false
     * IsUsedAuditionCardOrder : false
     * LevelName : 二级
     */

    private int Id;
    private String NickName;
    private String Mobile;
    private int Level;
    private String HeadImg;
    private String SexName;
    private boolean IsBoughtAuditionCardOrder;
    private boolean IsUsedAuditionCardOrder;
    private String LevelName;

    protected MyProfileBean(Parcel in) {
        Id = in.readInt();
        NickName = in.readString();
        Mobile = in.readString();
        Level = in.readInt();
        HeadImg = in.readString();
        SexName = in.readString();
        IsBoughtAuditionCardOrder = in.readByte() != 0;
        IsUsedAuditionCardOrder = in.readByte() != 0;
        LevelName = in.readString();
    }

    public static final Creator<MyProfileBean> CREATOR = new Creator<MyProfileBean>() {
        @Override
        public MyProfileBean createFromParcel(Parcel in) {
            return new MyProfileBean(in);
        }

        @Override
        public MyProfileBean[] newArray(int size) {
            return new MyProfileBean[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String HeadImg) {
        this.HeadImg = HeadImg;
    }

    public String getSexName() {
        return SexName;
    }

    public void setSexName(String SexName) {
        this.SexName = SexName;
    }

    public boolean isIsBoughtAuditionCardOrder() {
        return IsBoughtAuditionCardOrder;
    }

    public void setIsBoughtAuditionCardOrder(boolean IsBoughtAuditionCardOrder) {
        this.IsBoughtAuditionCardOrder = IsBoughtAuditionCardOrder;
    }

    public boolean isIsUsedAuditionCardOrder() {
        return IsUsedAuditionCardOrder;
    }

    public void setIsUsedAuditionCardOrder(boolean IsUsedAuditionCardOrder) {
        this.IsUsedAuditionCardOrder = IsUsedAuditionCardOrder;
    }

    public String getLevelName() {
        return LevelName;
    }

    public void setLevelName(String LevelName) {
        this.LevelName = LevelName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(NickName);
        parcel.writeString(Mobile);
        parcel.writeInt(Level);
        parcel.writeString(HeadImg);
        parcel.writeString(SexName);
        parcel.writeByte((byte) (IsBoughtAuditionCardOrder ? 1 : 0));
        parcel.writeByte((byte) (IsUsedAuditionCardOrder ? 1 : 0));
        parcel.writeString(LevelName);
    }
}
