package com.chewawa.baselibrary.base.presenter;


import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;


import com.chewawa.baselibrary.base.contract.BaseContract;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * nanfeifei
 * 2018/4/10 18:40
 *
 * @version 4.6.6
 */
public abstract class BasePresenterImpl<V extends BaseContract.View, T>
        implements DefaultLifecycleObserver {
    public T model;
    public V view;
    protected Reference<V> mViewRef;//view接口类型的弱引用

    public BasePresenterImpl(V view) {
        attachView(view);
        this.model = initModel();
        if(getView() != null){ //使用弱引用获取view
            this.view = getView();
        }else { //以防view为空导致闪退
            this.view = view;
        }
        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner)view).getLifecycle().addObserver(this);
            if (view != null && view instanceof LifecycleObserver) {
                ((LifecycleOwner)view).getLifecycle().addObserver((LifecycleObserver)view);
            }
        }

    }
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);//建立关联
    }
    public boolean isViewAttached(){
        return  mViewRef !=null && mViewRef.get() !=null;
    }
    public V getView() {
        if(mViewRef != null){
            return  mViewRef.get();
        }else {
            return null;
        }

    }

    public void detachView() {
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef =null;
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

    // 实例化
    public abstract T initModel();
}
