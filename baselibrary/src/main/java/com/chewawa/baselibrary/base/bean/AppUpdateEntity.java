package com.chewawa.baselibrary.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * nanfeifei
 * 2018/6/7 16:35
 *
 * @version 4.6.6
 */
public class AppUpdateEntity implements Parcelable {

    /**
     * id : 4
     * versionNum : 77
     * versionName : 4.6.5
     * updateInfo : 版本升级
     * isForcedUpdate : 0
     * updateTime : 2018-06-07T15:55:06
     * dateTime : 2018-06-07 15:55:06
     * app : 3
     * downUrl : http://www.chewawa.com.cn/Content/App/Android.apk
     */

    private int id;
    private int versionNum;   //版本号
    private String versionName;   //版本名称
    private String updateInfo;    //更新日志
    private int isOpenCheckUpdate;    //是否开启检查更新
    private int isForcedUpdate;    //是否强制更新,开启检查更新时有效
    private String updateTime;    //更新时间
    private String dateTime;
    private int app;
    private String downUrl;   //apk下载地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public int getIsOpenCheckUpdate() {
        return isOpenCheckUpdate;
    }

    public void setIsOpenCheckUpdate(int isOpenCheckUpdate) {
        this.isOpenCheckUpdate = isOpenCheckUpdate;
    }

    public boolean getIsForcedUpdate() {
        return isForcedUpdate>=1?true:false;
    }

    public void setIsForcedUpdate(int isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public AppUpdateEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.versionNum);
        dest.writeString(this.versionName);
        dest.writeString(this.updateInfo);
        dest.writeInt(this.isOpenCheckUpdate);
        dest.writeInt(this.isForcedUpdate);
        dest.writeString(this.updateTime);
        dest.writeString(this.dateTime);
        dest.writeInt(this.app);
        dest.writeString(this.downUrl);
    }

    protected AppUpdateEntity(Parcel in) {
        this.id = in.readInt();
        this.versionNum = in.readInt();
        this.versionName = in.readString();
        this.updateInfo = in.readString();
        this.isOpenCheckUpdate = in.readInt();
        this.isForcedUpdate = in.readInt();
        this.updateTime = in.readString();
        this.dateTime = in.readString();
        this.app = in.readInt();
        this.downUrl = in.readString();
    }

    public static final Creator<AppUpdateEntity> CREATOR = new Creator<AppUpdateEntity>() {
        @Override
        public AppUpdateEntity createFromParcel(Parcel source) {
            return new AppUpdateEntity(source);
        }

        @Override
        public AppUpdateEntity[] newArray(int size) {
            return new AppUpdateEntity[size];
        }
    };
}
