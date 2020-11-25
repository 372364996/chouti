package com.chewawa.baselibrary.base.model;


import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.zhouyou.http.EasyHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * nanfeifei
 * 2018/4/10 11:03
 *
 * @version 4.6.6
 */
public class BaseModelImpl implements DefaultLifecycleObserver {

    public List<Disposable> disposableList = new ArrayList<>();
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        for(int i = 0; i<disposableList.size(); i++){
            EasyHttp.cancelSubscription(disposableList.get(i));
        }
        owner.getLifecycle().removeObserver(this);
    }
}
