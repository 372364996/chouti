package com.hanzhi.onlineclassroom.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.ui.mine.fragment.WalletFragment;


/**
 * @class 钱包
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/7/21 9:41
 */
public class WalletActivity extends NBaseActivity {
    private Fragment mContent;
    public static void start(Context context) {
        Intent starter = new Intent(context, WalletActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_bind_fragment;
    }

    @Override
    protected void beforeContentView(Bundle savedInstanceState) {
        super.beforeContentView(savedInstanceState);
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null) {
            mContent = WalletFragment.newInstance();
        }
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_wallet);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
    }
}
