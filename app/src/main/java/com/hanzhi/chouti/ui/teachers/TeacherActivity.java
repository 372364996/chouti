package com.hanzhi.chouti.ui.teachers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.ui.selectclass.fragment.SelectClassFragment;
import com.hanzhi.chouti.ui.teachers.fragment.TeacherFragment;


/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/21 9:41
 */
public class TeacherActivity extends NBaseActivity {
    private Fragment mContent;
    ClassApplyBean classApplyBean;
    public static void start(Context context, ClassApplyBean classApplyBean) {
        Intent starter = new Intent(context, TeacherActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
        context.startActivity(starter);
    }
    @Override
    public boolean transparentStatusBar() {
        return true;
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_bind_fragment;
    }

    @Override
    protected void beforeContentView(Bundle savedInstanceState) {
        super.beforeContentView(savedInstanceState);
        classApplyBean = getIntent().getParcelableExtra("classApplyBean");
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null) {
            mContent = TeacherFragment.newInstance(classApplyBean);
        }
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_select_teacher);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
    }
}
