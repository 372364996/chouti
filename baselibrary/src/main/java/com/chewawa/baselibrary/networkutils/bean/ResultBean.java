package com.chewawa.baselibrary.networkutils.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 返回数据统一处理类
 * nanfeifei
 * 2017/6/15 11:13
 *
 * @version 3.7.0
 */
public class ResultBean implements Parcelable{


  /**
   * State : 1
   * Msg : 验证码已发送
   * Data : “”
   */

  private boolean success;
  private String msg;
  private String data;
  private int state;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    dest.writeString(this.msg);
    dest.writeString(this.data);
    dest.writeInt(this.state);
  }

  public ResultBean() {
  }

  protected ResultBean(Parcel in) {
    this.success = in.readByte() != 0;
    this.msg = in.readString();
    this.data = in.readString();
    this.state = in.readInt();
  }

  public static final Parcelable.Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
    @Override
    public ResultBean createFromParcel(Parcel source) {
      return new ResultBean(source);
    }

    @Override
    public ResultBean[] newArray(int size) {
      return new ResultBean[size];
    }
  };
}
