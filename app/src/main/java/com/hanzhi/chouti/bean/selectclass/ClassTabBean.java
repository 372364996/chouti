package com.hanzhi.chouti.bean.selectclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:41
 */
public class ClassTabBean implements Parcelable {


    /**
     * Id : 1
     * Name : 体验课
     * Description : 体验课
     */

    private int Id;
    private String Name;
    private String Description;

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.Description);
    }

    public ClassTabBean() {
    }

    protected ClassTabBean(Parcel in) {
        this.Id = in.readInt();
        this.Name = in.readString();
        this.Description = in.readString();
    }

    public static final Creator<ClassTabBean> CREATOR = new Creator<ClassTabBean>() {
        @Override
        public ClassTabBean createFromParcel(Parcel source) {
            return new ClassTabBean(source);
        }

        @Override
        public ClassTabBean[] newArray(int size) {
            return new ClassTabBean[size];
        }
    };
}
