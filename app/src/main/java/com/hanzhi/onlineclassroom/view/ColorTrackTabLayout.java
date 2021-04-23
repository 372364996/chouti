package com.hanzhi.onlineclassroom.view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class ColorTrackTabLayout extends TabLayout {


    public ColorTrackTabLayout(Context context) {
        super(context);
        initMinWidth();
    }

    public ColorTrackTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMinWidth();
    }

    public ColorTrackTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMinWidth();
    }

    private void initMinWidth() {
        try {
            Field field = TabLayout.class.getDeclaredField("scrollableTabMinWidth");
            field.setAccessible(true);
            // 设定最小的间距
            field.set(this, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
