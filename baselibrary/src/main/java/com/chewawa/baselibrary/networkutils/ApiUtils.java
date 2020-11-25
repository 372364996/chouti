package com.chewawa.baselibrary.networkutils;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 接口配置不同baseUrl
 * nanfeifei
 * 2018/2/5 13:17
 *
 * @version 1.0
 */
public class ApiUtils {
  public static final String DEFAULT = "/api/";
  @StringDef({DEFAULT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface UrlType{
  }
}
