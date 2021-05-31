package com.hanzhi.onlineclassroom.utils;

import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.chewawa.baselibrary.utils.SPUtils;

import static com.chewawa.baselibrary.utils.SPConstants.KEY_MOBILE;
import static com.chewawa.baselibrary.utils.SPConstants.KEY_TOKEN;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/8/18 18:43
 */
public class CommonUtil extends BaseCommonUtil {
    public static final String KEY_TEACHER_ID = "teacher_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_HEADIMG = "user_headimg";

    /**
     * 获取当前用户教师身份Id
     *
     * @return
     */
    public static int getTeacherId() {
        return (int) SPUtils.get(KEY_TEACHER_ID, 0);
    }

    /**
     * 保存当前用户教师身份Id
     *
     * @return
     */
    public static void saveTeacherId(int teacherId) {
        SPUtils.put(KEY_TEACHER_ID, teacherId);
    }

    /**
     * 获取userid
     *
     * @return
     */
    public static int getUserId() {
        return (int) SPUtils.get(KEY_USER_ID, 0);
    }

    /**
     * 保存userid
     *
     * @return
     */
    public static void saveUserId(int storeId) {
        SPUtils.put(KEY_USER_ID, storeId);
    }

    /**
     * 获取userName
     *
     * @return
     */
    public static String getUserName() {
        return (String) SPUtils.get(KEY_USER_NAME, "点击设置昵称");
    }

    /**
     * 保存userName
     *
     * @return
     */
    public static void saveUserName(String userName) {
        SPUtils.put(KEY_USER_NAME, userName);
    }
    /**
     * 获取userHeadImg
     *
     * @return
     */
    public static String getUserHeadImg() {
        return (String) SPUtils.get(KEY_USER_HEADIMG, "");
    }

    /**
     * 保存userHeadImg
     *
     * @return
     */
    public static void saveUserHeadImg(String userHeadImg) {
        SPUtils.put(KEY_USER_HEADIMG, userHeadImg);
    }


    /**
     * 清空用户数据
     */
    public static void clearUserData() {
        SPUtils.remove(KEY_TOKEN);
        SPUtils.remove(KEY_MOBILE);
        SPUtils.remove(KEY_USER_ID);
        SPUtils.remove(KEY_TEACHER_ID);
    }
}
