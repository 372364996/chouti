package com.chewawa.baselibrary.utils;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.networkutils.BaseConstants;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chewawa.baselibrary.utils.SPConstants.KEY_DEVICE_TOKEN;
import static com.chewawa.baselibrary.utils.SPConstants.KEY_DOMAIN_NAME;
import static com.chewawa.baselibrary.utils.SPConstants.KEY_MOBILE;
import static com.chewawa.baselibrary.utils.SPConstants.KEY_TOKEN;
import static com.chewawa.baselibrary.utils.SPConstants.KEY_UNREAD_MESSAGE_COUNT;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/13 18:10
 */
public class BaseCommonUtil {
    /**
     * 获取 显示信息
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * 手机号码正则判断
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(
                "^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isNoJSON(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return true;
        } else {
            if ((jsonString.startsWith("{") && jsonString.endsWith("}"))
                    || jsonString.startsWith("[") && jsonString.endsWith("]")) {
                //Log.i("JSON",jsonString);
                return false;
            } else {
                return true;
            }
        }
    }
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 判断用户是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        boolean isLogin = false;
        String token = BaseCommonUtil.getToken();
        if (!TextUtils.isEmpty(token)) {
            isLogin = true;
        }
        return isLogin;
    }

    /**
     * 获取Token
     *
     * @return
     */
    public static String getToken() {
        return (String) SPUtils.get(KEY_TOKEN, "");
    }

    /**
     * 保存Token
     *
     * @return
     */
    public static void saveToken(String token) {
        SPUtils.put(KEY_TOKEN, token);
    }

    public static void saveMobile(String mobile) {
        SPUtils.put(KEY_MOBILE, mobile);
    }

    public static String getMobile() {
        return (String) SPUtils.get(KEY_MOBILE, "");
    }
    /**
     * 保存域名
     * @param host
     */
    public static void saveDomainName(String host) {
        SPUtils.put(KEY_DOMAIN_NAME, host);
    }

    /**
     * 获取域名
     * @return
     */
    public static String getDomainName(String defaultValue) {
        return (String) SPUtils.get(KEY_DOMAIN_NAME, defaultValue);
    }
    /**
     * 保存设备唯一标识（取自友盟推送）
     * @param deviceToken
     */
    public static void saveDeviceToken(String deviceToken){
        SPUtils.put(KEY_DEVICE_TOKEN, deviceToken);
    }
    /**
     * 获取设备唯一标识（取自友盟推送）
     */
    public static String getDeviceToken(){
        return (String) SPUtils.get(KEY_DEVICE_TOKEN, "");
    }
    /**
     * 保存未读消息数
     * @param deviceToken
     */
    public static void saveUnreadMessageCount(int deviceToken){
        SPUtils.put(KEY_UNREAD_MESSAGE_COUNT, deviceToken);
    }
    /**
     * 获取未读消息数
     */
    public static int getUnreadMessageCount(){
        return (int) SPUtils.get(KEY_UNREAD_MESSAGE_COUNT, 0);
    }
    /**
     * 清空用户数据
     */
    public static void clearUserData() {
        SPUtils.remove(KEY_TOKEN);
        SPUtils.remove(KEY_MOBILE);
    }
    /**
     * 判断应用是否已经启动
     *
     * @param context     上下文对象
     * @return boolean
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        // 得到剪贴板管理器
        ClipboardManager clipboardManager = (ClipboardManager) BaseApplication.getInstance()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));
        if (clipboardManager.hasPrimaryClip()) {
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
    }
}
