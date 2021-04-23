package com.hanzhi.onlineclassroom.view;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * nanfeifei
 * 2018/4/23 15:55
 *
 * @version 4.6.6
 */
public class ConnectUsDialog extends AlertDialog {

    public Context context;
    @BindView(R.id.tv_weichat_1)
    TextView tvWeichat1;
    @BindView(R.id.tv_weichat_2)
    TextView tvWeichat2;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_web_url)
    TextView tvWebUrl;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String weichat1, weichat2, phone, webUrl;
    QMUIDialog qmuiDialog;
    private int mCurrentDialogStyle = R.style.DialogTheme2;
    public ConnectUsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    protected void init() {
        this.show();
        this.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_connect_us);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectUsDialog.this.cancel();
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
    public void setWeichat1(String weichat1){
        this.weichat1 = weichat1;
        tvWeichat1.setText(context.getString(R.string.wallet_weichat_1, weichat1));
    }
    public void setWeichat2(String weichat2){
        this.weichat2 = weichat2;
        tvWeichat2.setText(context.getString(R.string.wallet_weichat_2, weichat2));
    }
    public void setPhone(String phone){
        this.phone = phone;
        tvPhone.setText(context.getString(R.string.wallet_phone, phone));
    }
    public void setWebUrl(String webUrl){
        this.webUrl = webUrl;
        tvWebUrl.setText(context.getString(R.string.wallet_web_url, webUrl));
    }
    @OnClick({R.id.tv_weichat_1, R.id.tv_weichat_2, R.id.tv_phone, R.id.tv_web_url})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_weichat_1:{
                CommonUtil.copy(weichat1);
                copySuccess();
                break;
            }
            case R.id.tv_weichat_2:{
                CommonUtil.copy(weichat1);
                copySuccess();
                break;
            }
            case R.id.tv_phone:{
                callPhone(phone);
                break;
            }
            case R.id.tv_web_url:{
                startWeb(webUrl);
                break;
            }
        }
    }
    public void copySuccess(){
        if(context == null){
            return;
        }
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(context)
                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.wallet_to_weichat_tips)
                .addAction(context.getString(R.string.wallet_to_weichat_cancel), new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(context.getString(R.string.wallet_to_weichat_affirm), new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        getWechatApi();
                    }
                })
                .create(mCurrentDialogStyle);
        qmuiDialog.show();
    }
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        if(context == null){
            return;
        }
        if(TextUtils.isEmpty(phoneNum)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
    public void startWeb(String webUrl){
        if(context == null){
            return;
        }
        if(TextUtils.isEmpty(webUrl)){
            return;
        }
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(webUrl);
        intent.setData(content_url);
        context.startActivity(intent);
    }
    /**

     * 跳转到微信

     */

    private void getWechatApi(){

        try {

            Intent intent = new Intent(Intent.ACTION_MAIN);

            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setComponent(cmp);

            context.startActivity(intent);

        } catch (ActivityNotFoundException e)   {

            // TODO: handle   exception
            ToastUtils.showToast(R.string.wallet_to_weichat_no_install);

        }

    }
}
