package com.hanzhi.onlineclassroom.ui.teachers.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.chewawa.baselibrary.base.model.BaseModelImpl;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.bean.teachers.TeacherBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.teachers.contract.TeacherInfoContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/27 17:21
 */
public class TeacherInfoModel extends BaseModelImpl implements TeacherInfoContract.Model {
    @Override
    public void getTeacherInfo(int teacherId, TeacherInfoContract.OnGetTeacherInfoListener listener) {
        String url = Constants.GET_TEACHER_DETAIL_URL;
        Map<String, String> map = new HashMap<>();
        map.put("teacherId", String.valueOf(teacherId));
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onGetTeacherInfoFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){
                    TeacherBean teacherBean = JSONObject.parseObject(resultBean.getData(), TeacherBean.class);
                    listener.onGetTeacherInfoSuccess(teacherBean);
                }
            }
        });
        disposableList.add(disposable);
    }

    @Override
    public void collectTeacher(int teacherId, boolean isFans, TeacherInfoContract.OnCollectTeacherListener listener) {
        String url = Constants.COLLECT_TEACHER_APPLY;
        Map<String, String> map = new HashMap<>();
        map.put("teacherId", String.valueOf(teacherId));
        map.put("isFans", String.valueOf(isFans));
        Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
                listener.onCollectTeacherFailure(message);
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                Boolean isFans = JSONObject.parseObject(resultBean.getData(), Boolean.class);
                listener.onCollectTeacherSuccess(isFans);
            }
        });
        disposableList.add(disposable);
    }
}
