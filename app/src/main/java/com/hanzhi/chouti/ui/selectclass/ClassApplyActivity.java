package com.hanzhi.chouti.ui.selectclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.selectclass.ClassBean;
import com.hanzhi.chouti.ui.selectclass.contract.ClassApplyContract;
import com.hanzhi.chouti.ui.selectclass.presenter.ClassApplyPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:47
 */
public class ClassApplyActivity extends NBaseActivity<ClassApplyPresenter> implements ClassApplyContract.View {
    @BindView(R.id.tv_use_num)
    TextView tvUseNum;
    @BindView(R.id.tv_residue_num)
    TextView tvResidueNum;
    @BindView(R.id.tv_use_info)
    TextView tvUseInfo;
    @BindView(R.id.ll_title_lay)
    LinearLayout llTitleLay;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_session_name)
    TextView tvSessionName;
    @BindView(R.id.tv_class_time)
    TextView tvClassTime;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    ClassApplyBean classApplyBean;
    public static void start(Context context, ClassApplyBean classApplyBean) {
        Intent starter = new Intent(context, ClassApplyActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
        context.startActivity(starter);
    }
    @Override
    public int initLoadResId() {
        return R.layout.activity_class_apply;
    }

    @Override
    protected void initView() {
        classApplyBean = getIntent().getParcelableExtra("classApplyBean");
        initToolBar();
        toolbarLay.setTitle(R.string.title_class_apply);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        presenter.getClassApplyData(classApplyBean);
    }

    @Override
    public ClassApplyPresenter initPresenter() {
        return new ClassApplyPresenter(this);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_submit:{
                break;
            }
        }
    }

    @Override
    public void setClassApplyData(ClassBean classBean) {

    }
}
