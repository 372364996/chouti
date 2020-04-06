package com.example.choutidemo.ui.teachers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeacherViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TeacherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("外教");
    }

    public LiveData<String> getText() {
        return mText;
    }
}