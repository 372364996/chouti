package com.hanzhi.onlineclassroom.ui.appointment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.chewawa.baselibrary.view.XProgressDialog;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTabBean;
import com.hanzhi.onlineclassroom.bean.appointment.AppointmentTimeBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.appointment.adapter.SetAppointmentTimeAdapter;
import com.hanzhi.onlineclassroom.ui.login.ChangePasswordActivity;
import com.hanzhi.onlineclassroom.ui.login.LoginActivity;
import com.hanzhi.onlineclassroom.ui.selectclass.SelectClassActivity;
import com.hanzhi.onlineclassroom.ui.teachers.TeacherActivity;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.hanzhi.onlineclassroom.utils.RequestParamsUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class SetAppointmentTimeChildFragment extends BaseRecycleViewFragment<AppointmentTimeBean> implements View.OnClickListener {
    public Button btnSubmit;
    public static final String Date = "date";
    String date;
    public int teacherId;
    ClassApplyBean classApplyBean;
    QMUIDialog qmuiDialog;
    private int mCurrentDialogStyle = R.style.DialogTheme2;

    public static SetAppointmentTimeChildFragment newInstance(String date, ClassApplyBean classApplyBean) {
        SetAppointmentTimeChildFragment appointmentTimeChildFragment = new SetAppointmentTimeChildFragment();
        Bundle args = new Bundle();
        args.putString(Date, date);
        args.putParcelable("classApplyBean", classApplyBean);
        appointmentTimeChildFragment.setArguments(args);
        return appointmentTimeChildFragment;
    }

    @Override
    protected String getUrl() {
        return Constants.GET_CLASS_TIME_URL;
    }

    @Override
    protected Map<String, Object> getParams() {
        params.put("size", 200);
        params.put("date", date);
        params.putAll(RequestParamsUtils.getClassApplyParams(classApplyBean));
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if (getArguments() != null) {
            classApplyBean = getArguments().getParcelable("classApplyBean");
            date = getArguments().getString(Date);
        }
        ((SetAppointmentTimeAdapter) baseRecycleViewAdapter).enableCheckMode();
        ((SetAppointmentTimeAdapter) baseRecycleViewAdapter).setSingleMode(false);
        swipeRefresh.setEnabled(false);
        setEnableLoadMore(false);
    }

    @Override
    protected Class<AppointmentTimeBean> getResultClass() {
        return AppointmentTimeBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<AppointmentTimeBean> getAdapter() {
        return new SetAppointmentTimeAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 5);
    }

    @Override
    protected View addFooterView() {
        footerView = inflater.inflate(R.layout.view_footer_submit, null);
        btnSubmit = footerView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        footerView.setVisibility(View.GONE);
        return footerView;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        AppointmentTimeBean appointmentTimeBean = (AppointmentTimeBean) adapter.getItem(position);
//        if(appointmentTimeBean == null||!appointmentTimeBean.getIsCanUse()){
//            return;
//        }

        ((SetAppointmentTimeAdapter) adapter).clickItem(position, false);
    }

    @Override
    public void setListData(boolean isRefresh, List list, boolean isShowMore) {
        for (int i = 0; i < list.size(); i++) {
            if (((AppointmentTimeBean) list.get(i)).getIsCanUse()) {
                ((AppointmentTimeBean) list.get(i)).setChecked(true);
            }
        }
        super.setListData(isRefresh, list, isShowMore);
        footerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: {
                if (baseRecycleViewAdapter == null) {
                    return;
                }
                List<AppointmentTimeBean> checkedItems = ((SetAppointmentTimeAdapter) baseRecycleViewAdapter).getCheckedItems();
//                if (checkedItems == null || checkedItems.size() == 0) {
//                    ToastUtils.showToast(R.string.appointment_time_no_checked_tips);
//                    return;
//                }
                qmuiDialog = new QMUIDialog.MessageDialogBuilder(getActivity())
                        .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.set_appointment_time_affirm)
                        .addAction(getString(R.string.appointment_time_reelect), new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(getString(R.string.my_class_tips_affirm), new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {

                                dialog.dismiss();
                                loadingDialog = new XProgressDialog(getActivity(), "设置中,请稍等...");
                                loadingDialog.show();
                                String url = Constants.ADD_TEACHER_TIME;
                                Map<String, Object> map = new HashMap<>();
                                map.put("teacherId", String.valueOf(CommonUtil.getTeacherId()));
                                List<String> classTimeList = new ArrayList<String>();
                                if (checkedItems.isEmpty()) {
                                    classTimeList.add(date);
                                    map.put("isChecked", false);
                                } else {
                                    for (AppointmentTimeBean item :
                                            checkedItems) {
                                        classTimeList.add(item.getDateTimeStr());
                                    }
                                }

                                map.put("classTimeList", classTimeList);
                                Disposable disposable = HttpManager.post(url).upJsonObject(map).execute(new ApiCallBack() {
                                    @Override
                                    public void onError(int status, String message) {
                                        loadingDialog.hide();
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onSuccess(ResultBean resultBean) {
                                        loadingDialog.hide();
                                        Toast.makeText(getActivity(), resultBean.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .create(mCurrentDialogStyle);
                qmuiDialog.show();
                break;
            }
        }
    }
}
