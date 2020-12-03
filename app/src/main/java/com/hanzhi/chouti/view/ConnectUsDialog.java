package com.hanzhi.chouti.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hanzhi.chouti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

}
