package com.chewawa.baselibrary.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.chewawa.baselibrary.BaseApplication;


public class ToastUtils {
	public ToastUtils() {

	}

	public static void showToast(String toast) {
		if(TextUtils.isEmpty(toast)){
			return;
		}
		if (null == mToast) {
			mToast = Toast.makeText(BaseApplication.getInstance(), toast, Toast.LENGTH_SHORT);
		} else {
			if(!TextUtils.isEmpty(toast)){
				mToast.setText(toast);
			}
			
		}

		mToast.show();
	}

	public static void showToast(int toast) {
		if(toast == 0){
			return;
		}
		if (null == mToast) {
			mToast = Toast.makeText(BaseApplication.getInstance(), toast, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(toast);
		}

		mToast.show();
	}

	public static void cancel() {
		if (null != mToast) {
			mToast.cancel();
		}
	}

	public static Toast mToast = null;
}
