package com.hanzhi.onlineclassroom.utils;

import android.text.TextUtils;

import com.hanzhi.onlineclassroom.bean.ClassApplyBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 21:16
 */
public class RequestParamsUtils {
    public static Map<String, String> getClassApplyParams(ClassApplyBean classApplyBean){
        Map<String, String> map = new HashMap<>();
        if(classApplyBean != null){
            if(classApplyBean.getClassId() != 0){
                map.put("classId", String.valueOf(classApplyBean.getClassId()));
            }
            if(classApplyBean.getTeacherId() != 0){
                map.put("teacherId", String.valueOf(classApplyBean.getTeacherId()));
            }
            if(!TextUtils.isEmpty(classApplyBean.getDateTimeStr())){
                map.put("classTime", classApplyBean.getDateTimeStr());
            }
        }
        return map;

    }
}
