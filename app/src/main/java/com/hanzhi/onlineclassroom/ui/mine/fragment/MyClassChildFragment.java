package com.hanzhi.onlineclassroom.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewFragment;
import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.mine.MyClassBean;
import com.hanzhi.onlineclassroom.bean.mine.MyClassTabBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.appointment.AppointmentTimeActivity;
import com.hanzhi.onlineclassroom.ui.mine.MyClassDetailActivity;
import com.hanzhi.onlineclassroom.ui.mine.adapter.MyClassAdapter;
import com.hanzhi.onlineclassroom.ui.mine.contract.MyClassContract;
import com.hanzhi.onlineclassroom.ui.mine.presenter.MyClassPresenter;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;
import java.util.Map;

/**
 * @class 我的课程
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 17:24
 */
public class MyClassChildFragment extends BaseRecycleViewFragment<MyClassBean> implements MyClassContract.View {
    public static final String ID = "id";
    int id;
    MyClassPresenter myClassPresenter;
    QMUIDialog qmuiDialog;
    private int mCurrentDialogStyle = R.style.DialogTheme2;
    MyClassBean myClassBean;

    public static MyClassChildFragment newInstance(int id) {
        MyClassChildFragment myClassChildFragment = new MyClassChildFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        myClassChildFragment.setArguments(args);
        return myClassChildFragment;
    }

    @Override
    protected String getUrl() {
        if (CommonUtil.getTeacherId() > 0) {
            return Constants.GET_TEACHER_CLASS_URL;
        } else {
            return Constants.GET_MY_CLASS_URL;
        }
    }

    @Override
    protected Map<String, Object> getParams() {
        if (CommonUtil.getTeacherId() > 0) {
            params.put("teacherId", CommonUtil.getTeacherId());
        }
        params.put("status", id);
        params.put("size", 20);
        return params;
    }

    @Override
    public void initView() {
        super.initView();
        if (getArguments() != null) {
            id = getArguments().getInt(ID, 0);
        }
    }

    @Override
    public BasePresenterImpl initPresenter() {
        myClassPresenter = new MyClassPresenter(this);
        return super.initPresenter();
    }

    @Override
    protected Class<MyClassBean> getResultClass() {
        return MyClassBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter<MyClassBean> getAdapter() {
        return new MyClassAdapter();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemChildClick(adapter, view, position);
        myClassBean = (MyClassBean) adapter.getItem(position);
        if (myClassBean == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_reapply: {
                AppointmentTimeActivity.start(getActivity(), null);
                break;
            }
            case R.id.btn_cancel: {
                myClassPresenter.cancelClass(myClassBean.getNumber());
                break;
            }
            case R.id.btn_review: {
                MyClassDetailActivity.start(getActivity(), myClassBean.getClassId(), myClassBean.getNumber(),myClassBean.getUserId());
                break;
            }
            case R.id.btn_join_class: {
//                MyClassDetailActivity.start(getActivity(), myClassBean.getClassId(), myClassBean.getNumber());
                myClassPresenter.joinClass(myClassBean.getNumber());
                break;
            }
        }
    }

//    @Override
////    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////        super.onItemClick(adapter, view, position);
////        myClassBean = (MyClassBean) adapter.getItem(position);
////
////    }

    @Override
    public void setTabList(List<MyClassTabBean> list, List<String> titleList) {

    }

    @Override
    public void refreshList() {
        onRefresh();
    }

    @Override
    public void joinClassSuccess() {
        if (myClassBean == null) {
            return;
        }
        MyClassDetailActivity.start(getActivity(), myClassBean.getClassId(), myClassBean.getNumber(),myClassBean.getUserId());
    }

    @Override
    public void joinClassTips(String message) {
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(getActivity())
                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                .setTitle(R.string.dialog_title)
                .setMessage(message)
                .addAction(getString(R.string.my_class_tips_affirm), new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle);
        qmuiDialog.show();
    }
}
