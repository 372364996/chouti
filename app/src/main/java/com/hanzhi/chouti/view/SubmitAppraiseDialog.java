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
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.ColorInt;
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
public class SubmitAppraiseDialog extends AlertDialog {

    public Context context;
    @BindView(R.id.rb_ranking)
    RatingBar rbRanking;
    @BindView(R.id.et_appraise)
    EditText etAppraise;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    OnAlertDialogListener listener;
    public SubmitAppraiseDialog(@NonNull Context context) {
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
        setContentView(R.layout.view_dialog_submit_appraise);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    float ranking = rbRanking.getRating();
                    String editText = etAppraise.getText().toString();
                    listener.onDialogAffirm(ranking, editText);
                    SubmitAppraiseDialog.this.cancel();
                }

            }
        });
    }

    @Override
    public void show() {
        super.show();
        if (etAppraise == null) {
            return;
        }
        etAppraise.setText(null);
    }


    /**
     * 设置确认/取消监听
     */
    public void setOnAlertDialogListener(OnAlertDialogListener listener) {
        this.listener = listener;
    }


    public interface OnAlertDialogListener {
        void onDialogAffirm(float ranking, String editText);
    }
}
