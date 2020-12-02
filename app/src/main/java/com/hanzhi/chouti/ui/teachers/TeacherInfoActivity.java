package com.hanzhi.chouti.ui.teachers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.chewawa.baselibrary.view.viewpager.CommonTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.event.RefreshCollectTeacherEvent;
import com.hanzhi.chouti.event.RefreshTeacherListEvent;
import com.hanzhi.chouti.ui.appointment.fragment.AppointmentTimeFragment;
import com.hanzhi.chouti.ui.teachers.contract.TeacherInfoContract;
import com.hanzhi.chouti.ui.teachers.fragment.AppraiseFragment;
import com.hanzhi.chouti.ui.teachers.presenter.TeacherInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherInfoActivity extends NBaseActivity<TeacherInfoPresenter> implements TeacherInfoContract.View, CommonTabPagerAdapter.TabPagerListener {

    @BindView(R.id.iv_head_img)
    CircleImageView ivHeadImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_ranking)
    ImageView ivRanking;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_school_name)
    TextView tvSchoolName;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_playing)
    ImageView ivPlaying;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    ClassApplyBean classApplyBean;
    boolean isFans;
    int from;
    private CommonTabPagerAdapter adapter;
    public static final int FROM_TEACHER_LIST = 1001;
    public static final int FROM_COLLECT_LIST = 1002;
    public static void start(Context context, ClassApplyBean classApplyBean, int from) {
        Intent starter = new Intent(context, TeacherInfoActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
        starter.putExtra("from", from);
        context.startActivity(starter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_teacher_info;
    }
    @Override
    protected void beforeContentView(Bundle savedInstanceState) {
        super.beforeContentView(savedInstanceState);
        classApplyBean = getIntent().getParcelableExtra("classApplyBean");
    }
    @Override
    protected void initView() {
        from = getIntent().getIntExtra("from", 0);
        initToolBar();
        toolbarLay.setTitle(R.string.title_teacher_detail);
        List<String> titleList = new ArrayList<>();
        titleList.add(getString(R.string.teacher_detail_table_time));
        titleList.add(getString(R.string.teacher_detail_table_appraise));
        adapter = new CommonTabPagerAdapter(getSupportFragmentManager(), titleList.size()
                , titleList, this);
        adapter.setListener(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titleList.size());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.teacher_info_selected_time, mContent)
//                .commit();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if(classApplyBean != null){
            presenter.getTeacherInfo(classApplyBean.getTeacherId());
        }

    }

    @Override
    public TeacherInfoPresenter initPresenter() {
        return new TeacherInfoPresenter(this);
    }

    @OnClick({R.id.iv_playing, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_playing:{
                break;
            }
            case R.id.iv_collect:{
                if(classApplyBean != null){
                    presenter.collectTeacher(classApplyBean.getTeacherId(), !isFans);
                }
                break;
            }
        }
    }

    @Override
    public void setTeacherInfo(TeacherBean teacherBean) {
        this.isFans = teacherBean.isFans();
        ivCollect.setImageResource(isFans ? R.drawable.favorites_fill : R.drawable.favorites);
        imageLoaderUtils.loadImage(teacherBean.getHeadImg(), ivHeadImg, R.drawable.hanzhilogo);
        tvTeacherName.setText(teacherBean.getName());
        tvSchoolName.setText(teacherBean.getUniversity());
        tvScore.setText(teacherBean.getAvgScore());
        tvInfo.setText(teacherBean.getDescription());
    }

    @Override
    public void collectSuccess(boolean isFans) {
        this.isFans = isFans;
        ivCollect.setImageResource(isFans ? R.drawable.favorites_fill : R.drawable.favorites);
        if(FROM_COLLECT_LIST == from){
            EventBus.getDefault().post(new RefreshCollectTeacherEvent());
        }else {
            EventBus.getDefault().post(new RefreshTeacherListEvent());
        }
    }

    @Override
    public Fragment getFragment(int position) {
        if(position == 0){
            return AppointmentTimeFragment.newInstance(classApplyBean);
        }else if(position == 1){
            return AppraiseFragment.newInstance(classApplyBean);
        }
        return AppointmentTimeFragment.newInstance(classApplyBean);
    }
}