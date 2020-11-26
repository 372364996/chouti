package com.hanzhi.chouti.ui.appointment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YuYueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YuYueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("预约");
    }

    public LiveData<String> getText() {
        return mText;
    }
}