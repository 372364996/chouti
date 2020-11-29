package com.hanzhi.chouti.utils;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liqin on 2016/1/19.
 */
public class DateUtils {
  private static final SimpleDateFormat YMDHMS =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
  private static final SimpleDateFormat YMD =
      new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
  private static final SimpleDateFormat YMDHM =
      new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
  public static final SimpleDateFormat YMDS =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
  public static final SimpleDateFormat YMD_DOT =
          new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
  public static String getNextDate(String dateStr, int count) {
    try {
      Date date = YMDS.parse(dateStr);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.MONTH, count);
      return YMD.format(calendar.getTime()).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dateStr;
  }
  public static Date getDate(SimpleDateFormat simpleDateFormat, String dateStr) {
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static String getYmd(String time) {
    if (TextUtils.isEmpty(time)) {
      return "";
    }
    try {
      return YMD.format(YMDHMS.parse(time)).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return time;
  }

  public static String getYmdhm(String time) {
    if (TextUtils.isEmpty(time)) {
      return "";
    }
    try {
      return YMDHM.format(YMDHMS.parse(time)).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return time;
  }
}
