package com.chewawa.baselibrary.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.utils.BaseCommonUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 无网络时顶部的View
 * nanfeifei
 * 2018/2/24 10:09
 *
 * @version 3.7.0
 */
public class NoNetWorkView extends FrameLayout {

    @BindView(R2.id.iv_left)
    ImageView ivLeft;
    @BindView(R2.id.net_title_tv)
    TextView netTitleTv;
    @BindView(R2.id.net_view_rl)
    RelativeLayout netViewRl;
    private Context context;

    public NoNetWorkView(Context context) {
        super(context);
        init(context);
    }

    public NoNetWorkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoNetWorkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NoNetWorkView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_network_bar_lay, this);
        ButterKnife.bind(this);
        this.context = context;
        //            //无网提示框显示在状态栏之下
        RelativeLayout.LayoutParams netParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        netParams.setMargins(0, QMUIStatusBarHelper.getStatusbarHeight(context), 0, 0);
        netViewRl.setLayoutParams(netParams);
        netViewRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到系统的网络设置界面
                Intent intent = null;
                // 先判断当前系统版本
                if (Build.VERSION.SDK_INT > 10) {  // 3.0以上
                    intent = new Intent(Settings
                            .ACTION_SETTINGS);
                } else {
                    intent = new Intent();
                    intent.setClassName("com.android.settings",
                            Settings.ACTION_SETTINGS);
                }
                if ((context instanceof Application)) {
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        });
    }

}
