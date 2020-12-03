package com.hanzhi.chouti.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.ui.mine.fragment.MineFragment;
import com.hanzhi.chouti.ui.selectclass.fragment.SelectClassFragment;


/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/21 9:41
 */
public class MineActivity extends NBaseActivity {
    private Fragment mContent;
    int position;
    public static void start(Context context, int position) {
        Intent starter = new Intent(context, MineActivity.class);
        starter.putExtra("position", position);
        context.startActivity(starter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_bind_fragment;
    }

    @Override
    protected void beforeContentView(Bundle savedInstanceState) {
        super.beforeContentView(savedInstanceState);
        position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null) {
            mContent = MineFragment.newInstance(position);
        }
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_mine);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
    }
}
