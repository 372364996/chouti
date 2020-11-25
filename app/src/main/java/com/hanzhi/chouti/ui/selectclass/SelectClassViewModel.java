package com.hanzhi.chouti.ui.selectclass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectClassViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SelectClassViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("选择课程");
    }

    public LiveData<String> getText() {
        return mText;
    }
}