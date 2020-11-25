package com.chewawa.baselibrary.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 页数信息
 * nanfeifei
 * 2017/6/20 18:31
 *
 * @version 3.7.0
 */
public class PageBean implements Parcelable {

  /**
   * pageIndex : 1
   * pageNumber : 1
   */

  public int pageIndex;

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public PageBean() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.pageIndex);
  }

  protected PageBean(Parcel in) {
    this.pageIndex = in.readInt();
  }

  public static final Creator<PageBean> CREATOR = new Creator<PageBean>() {
    @Override public PageBean createFromParcel(Parcel source) {
      return new PageBean(source);
    }

    @Override public PageBean[] newArray(int size) {
      return new PageBean[size];
    }
  };
}
