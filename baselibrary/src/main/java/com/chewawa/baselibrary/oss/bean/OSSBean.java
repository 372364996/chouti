package com.chewawa.baselibrary.oss.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2019/7/26 9:58
 */
public class OSSBean implements Parcelable {

    /**
     * AccessKeyId : STS.NK31ji7mib5s9yG9eDF4t9vyq
     * AccessKeySecret : 7HpDuCMsXQb6zXvygER2bP5a8HxjJ3hRWkvt9BQ1eUgK
     * SecurityToken : CAIS6wF1q6Ft5B2yfSjIr4iGetDd2rJI1feYO1/23WURSrtY1rPSkzz2IH5Of3ZtAOgasPkzlWpW6v8Slq94WpUAXxac3VG+Zz4So22beIPkl5Gfz95t0e+IewW6Dxr8w7WhAYHQR8/cffGAck3NkjQJr5LxaTSlWS7OU/TL8+kFCO4aRQ6ldzFLKc5LLw950q8gOGDWKOymP2yB4AOSLjIx41El2DMis/TinZfAskGPtjCglL9J/baWC4O/csxhMK14V9qIx+FsfsLDqnUOsUMVqf4u0PIZpWad4I3EU0M/6ASMdvCO4jLopoGcv0FQGoABTEXFJQIUECC4ls8wPFMZsLiBoTqGG/9K4cq2IsAuXbLUBQB+R8X6/RHYUcESuUZbwU3lkLWb2ATvxK7gKuRMXed1QQYvRORg8ecAQS69YVMasn9iRgOnm5Bxg3tnduSYlYvMpyNJz84sh5bbXQCLWDOImDwWjoH1OYVf5PjnL4o=
     * Expiration : 2019-07-26T10:43:47+08:00
     * Endpoint : oss-cn-beijing.aliyuncs.com
     * Bucket : cyb-bucket-1
     */

    private String AccessKeyId;
    private String AccessKeySecret;
    private String SecurityToken;
    private String Expiration;
    private String Endpoint;
    private String Bucket;

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String AccessKeyId) {
        this.AccessKeyId = AccessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String AccessKeySecret) {
        this.AccessKeySecret = AccessKeySecret;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String SecurityToken) {
        this.SecurityToken = SecurityToken;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String Expiration) {
        this.Expiration = Expiration;
    }

    public String getEndpoint() {
        return Endpoint;
    }

    public void setEndpoint(String Endpoint) {
        this.Endpoint = Endpoint;
    }

    public String getBucket() {
        return Bucket;
    }

    public void setBucket(String Bucket) {
        this.Bucket = Bucket;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AccessKeyId);
        dest.writeString(this.AccessKeySecret);
        dest.writeString(this.SecurityToken);
        dest.writeString(this.Expiration);
        dest.writeString(this.Endpoint);
        dest.writeString(this.Bucket);
    }

    public OSSBean() {
    }

    protected OSSBean(Parcel in) {
        this.AccessKeyId = in.readString();
        this.AccessKeySecret = in.readString();
        this.SecurityToken = in.readString();
        this.Expiration = in.readString();
        this.Endpoint = in.readString();
        this.Bucket = in.readString();
    }

    public static final Creator<OSSBean> CREATOR = new Creator<OSSBean>() {
        @Override
        public OSSBean createFromParcel(Parcel source) {
            return new OSSBean(source);
        }

        @Override
        public OSSBean[] newArray(int size) {
            return new OSSBean[size];
        }
    };
}
