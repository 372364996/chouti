package com.chewawa.baselibrary.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.R;


/**
 * nanfeifei
 * 2018/4/9 11:14
 *
 * @version 4.6.6
 */
public class AuthCodeCountDownTimer extends CountDownTimer {
    private TextView authButton;
    private String tickText;
    String finishText;
    int tickBackgroundResource;
    int finishBackgroundResource;
    int tickTextColor;
    int finishTextColor;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public AuthCodeCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public AuthCodeCountDownTimer(TextView authButton, long millisInFuture, long countDownInterval){
        this(millisInFuture, countDownInterval);
        this.authButton = authButton;
    }

    @Override public void onTick(long millisUntilFinished) {
        Context context = BaseApplication.getInstance();
        if(TextUtils.isEmpty(tickText)){
            authButton.setText(context.getString(R.string.auth_code_tick_text, context.getString(R.string.auth_code_count_down_timer
                    , millisUntilFinished/1000), context.getString(R.string.auth_code_get_auth_code)));
        }else {
            authButton.setText(context.getString(R.string.auth_code_tick_text, context.getString(R.string.auth_code_count_down_timer
                    , millisUntilFinished/1000), finishText));
        }
        if(tickBackgroundResource == 0){
            authButton.setBackgroundResource(R.drawable.rectangle_round_corner3_stroke_light_gray);
        }else {
            authButton.setBackgroundResource(tickBackgroundResource);
        }
        if(tickTextColor == 0){
            authButton.setTextColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_color_99));
        }else {
            authButton.setTextColor(ContextCompat.getColor(BaseApplication.getInstance(), tickTextColor));
        }
        authButton.setClickable(false);
    }

    @Override public void onFinish() {
        if(TextUtils.isEmpty(finishText)){
            authButton.setText(BaseApplication.getInstance().getString(R.string.auth_code_get_auth_code));
        }else {
            authButton.setText(finishText);
        }
        if(finishBackgroundResource == 0){
            authButton.setBackgroundResource(R.drawable.rectangle_round_corner3_stroke_primary);
        }else {
            authButton.setBackgroundResource(finishBackgroundResource);
        }
        if(finishTextColor == 0){
            authButton.setTextColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.color_button_primary));
        }else {
            authButton.setTextColor(ContextCompat.getColor(BaseApplication.getInstance(), finishTextColor));
        }
        authButton.setClickable(true);
    }
    public void setTickText(String tickText){
        this.tickText = tickText;
    }
    public void setFinishText(String finishText){
        this.finishText = finishText;
    }
    public void setTickBackgroundResource(int tickBackgroundResource) {
        this.tickBackgroundResource = tickBackgroundResource;
    }
    public void setFinishBackgroundResource(int finishBackgroundResource) {
        this.finishBackgroundResource = finishBackgroundResource;
    }
    public void setTickTextColor(int tickTextColor){
        this.tickTextColor = tickTextColor;
    }
    public void setFinishTextColor(int finishTextColor){
        this.finishTextColor = finishTextColor;
    }
}
