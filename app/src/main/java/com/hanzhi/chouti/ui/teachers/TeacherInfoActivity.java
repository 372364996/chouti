package com.hanzhi.chouti.ui.teachers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.teachers.TeacherBean;
import com.hanzhi.chouti.ui.appointment.fragment.AppointmentTimeFragment;
import com.hanzhi.chouti.ui.teachers.contract.TeacherInfoContract;
import com.hanzhi.chouti.ui.teachers.presenter.TeacherInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherInfoActivity extends NBaseActivity<TeacherInfoPresenter> implements TeacherInfoContract.View {

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
    @BindView(R.id.cb_appraise)
    CheckBox cbAppraise;
    @BindView(R.id.iv_playing)
    ImageView ivPlaying;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    private Fragment mContent;
    ClassApplyBean classApplyBean;
    public static void start(Context context, ClassApplyBean classApplyBean) {
        Intent starter = new Intent(context, TeacherInfoActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
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
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null) {
            mContent = AppointmentTimeFragment.newInstance(classApplyBean);
        }
    }
    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_teacher_detail);
//        //必需继承FragmentActivity,嵌套fragment只需要这行代码
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.teacher_info_selected_time, AppointmentTimeFragment.newInstance(teacherId))
//                .commitAllowingStateLoss();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.teacher_info_selected_time, mContent)
                .commit();
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

    @OnClick(R.id.iv_playing)
    public void onViewClicked() {
    }

    @Override
    public void setTeacherInfo(TeacherBean teacherBean) {
        imageLoaderUtils.loadImage(teacherBean.getHeadImg(), ivHeadImg, R.drawable.hanzhilogo);
        tvTeacherName.setText(teacherBean.getName());
        tvSchoolName.setText(teacherBean.getUniversity());
        tvScore.setText(teacherBean.getAvgScore());
        tvInfo.setText(teacherBean.getDescription());
    }
}