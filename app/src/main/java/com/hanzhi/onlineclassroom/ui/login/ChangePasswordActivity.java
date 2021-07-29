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
 * @class 更改密码
 */
public class ChangePasswordActivity extends NBaseActivity {

    @BindView(R.id.toolbar_lay)
    QMUITopBar toolbarLay;
    @BindView(R.id.et_password1)
    EditText etPassword1;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.et_password3)
    EditText etPassword3;
    @BindView(R.id.btn_change_password)
    Button btnChangePassword;

    @Override
    public int initLoadResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        initToolBar();
        toolbarLay.setTitle(R.string.title_change_password);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_change_password)
    public void onViewClicked() {
    }
}
