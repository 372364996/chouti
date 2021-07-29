package com.hanzhi.onlineclassroom.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.onlineclassroom.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @class 联系助教
 */
public class ContactActivity extends NBaseActivity {

    @BindView(R.id.toolbar_lay)
    QMUITopBar toolbarLay;

    @Override
    public int initLoadResId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_contact);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
