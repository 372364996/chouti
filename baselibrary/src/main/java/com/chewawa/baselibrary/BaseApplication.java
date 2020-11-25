package com.chewawa.baselibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chewawa.baselibrary.utils.LogUtils;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;


/**
 * 父类Application
 * nanfeifei
 * 2017/5/8 17:18
 *
 * @version 3.7.0
 */
public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static BaseApplication application = null;

    public static BaseApplication getInstance() {
        return application;
    }

    List<Activity> activityLinkedList;
    public static Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //initLocation();
        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(this);
        setRxJavaErrorHandler();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activityLinkedList.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityLinkedList.remove(activity);
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.d("throw test");
            }
        });
    }

    public void exitAppList() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
    }
    public abstract void exitLogin(boolean isShowDialog, String message);
    /**
     * 获取栈顶Activity
     *
     * @return
     */
    public  Activity getTopActivity() {
        return activity;
    }
}
