package com.hanzhi.onlineclassroom.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.MainActivity;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

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
        String pwd1 = etPassword1.getText().toString().trim();
        String pwd2 = etPassword2.getText().toString().trim();
        String pwd3 = etPassword3.getText().toString().trim();
        if (pwd1 == null || pwd1.length() == 0) {
            Toast.makeText(ChangePasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
        }
        if (!pwd2.equals(pwd3)) {
            Toast.makeText(ChangePasswordActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            String url = Constants.UPDATE_USER_INFO;
            Map<String, String> map = new HashMap<>();
            map.put("userid", String.valueOf(CommonUtil.getUserId()));
            map.put("passwordNew", pwd3);
            map.put("passwordOld", pwd1);
            Disposable disposable = HttpManager.get(url).params(map).execute(new ApiCallBack() {
                @Override
                public void onError(int status, String message) {
                    Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(ResultBean resultBean) {
                    Toast.makeText(ChangePasswordActivity.this, resultBean.getMsg(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}
