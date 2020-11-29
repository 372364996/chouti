package com.hanzhi.chouti.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.selectclass.ClassBean;
import com.hanzhi.chouti.ui.selectclass.adapter.ClassDetailAdapter;
import com.hanzhi.chouti.ui.selectclass.contract.ClassDetailContract;
import com.hanzhi.chouti.ui.selectclass.presenter.ClassDetailPresenter;

import butterknife.BindView;

/**
 * @class 课程详情
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 13:58
 */
public class MyClassDetailActivity extends NBaseActivity<ClassDetailPresenter> implements ClassDetailContract.View, ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.progress_horizontal)
    ProgressBar progressHorizontal;
    @BindView(R.id.vp_class_detail)
    ViewPager vpClassDetail;
    ClassDetailAdapter classDetailAdapter;
    int classId;
    ClassBean classBean;
    public static void start(Context context, int classId) {
        Intent starter = new Intent(context, MyClassDetailActivity.class);
        starter.putExtra("classId", classId);
        context.startActivity(starter);
    }
    @Override
    public int initLoadResId() {
        return R.layout.activity_my_class_detail;
    }

    @Override
    protected void initView() {
        classId = getIntent().getIntExtra("classId", 0);
        initToolBar();
        toolbarLay.setTitle(R.string.title_class_detail);
        vpClassDetail.addOnPageChangeListener(this);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        presenter.getClassDetailData(classId);
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
        tvPage.setText("1/"+classBean.getClassMaterials().size());
        progressHorizontal.setMax(classBean.getClassMaterials().size());
        progressHorizontal.setProgress(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(classBean == null){
            return;
        }
        int index = position + 1;
        tvPage.setText(index+"/"+classBean.getClassMaterials().size());
        progressHorizontal.setProgress(index);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
