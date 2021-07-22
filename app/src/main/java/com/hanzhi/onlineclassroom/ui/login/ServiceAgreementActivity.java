package com.hanzhi.onlineclassroom.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.ClassApplyBean;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassBean;
import com.hanzhi.onlineclassroom.ui.appointment.AppointmentTimeActivity;
import com.hanzhi.onlineclassroom.ui.selectclass.adapter.ClassDetailAdapter;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.ClassDetailContract;
import com.hanzhi.onlineclassroom.ui.selectclass.presenter.ClassDetailPresenter;
import com.hanzhi.onlineclassroom.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @class 课程详情
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 13:58
 */
public class ServiceAgreementActivity extends NBaseActivity<ClassDetailPresenter> implements ClassDetailContract.View, ViewPager.OnPageChangeListener {
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.progress_horizontal)
    ProgressBar progressHorizontal;
    @BindView(R.id.vp_class_detail)
    ViewPager vpClassDetail;
    ClassDetailAdapter classDetailAdapter;
    ClassApplyBean classApplyBean;
    ClassBean classBean;

    public static void start(Context context, ClassApplyBean classApplyBean) {
        Intent starter = new Intent(context, ServiceAgreementActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
        context.startActivity(starter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_class_detail;
    }

    @Override
    protected void initView() {
        classApplyBean = getIntent().getParcelableExtra("classApplyBean");
        initToolBar();
        toolbarLay.setTitle(R.string.title_class_detail);
        if (CommonUtil.getTeacherId() > 0) {
            btnSubmit.setVisibility(View.INVISIBLE);
        }
        vpClassDetail.addOnPageChangeListener(this);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (classApplyBean != null) {
            presenter.getClassDetailData(classApplyBean.getClassId());
        }
    }

    @Override
    public ClassDetailPresenter initPresenter() {
        return new ClassDetailPresenter(this);
    }

    @Override
    public void setClassDetailData(ClassBean classBean) {
        this.classBean = classBean;
        classDetailAdapter = new ClassDetailAdapter(this, classBean.getClassMaterials());
        vpClassDetail.setAdapter(classDetailAdapter);
        tvPage.setText("1/" + classBean.getClassMaterials().size());
        progressHorizontal.setMax(classBean.getClassMaterials().size());
        progressHorizontal.setProgress(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (classBean == null) {
            return;
        }
        int index = position + 1;
        tvPage.setText(index + "/" + classBean.getClassMaterials().size());
        progressHorizontal.setProgress(index);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: {
                if (classApplyBean == null) {
                    classApplyBean = new ClassApplyBean();
                }
                if (TextUtils.isEmpty(classApplyBean.getDateTimeStr())) {
                    AppointmentTimeActivity.start(ServiceAgreementActivity.this, classApplyBean);
                } else {
                    ServiceAgreementActivity.start(ServiceAgreementActivity.this, classApplyBean);
                }
                break;
            }
        }
    }
}
