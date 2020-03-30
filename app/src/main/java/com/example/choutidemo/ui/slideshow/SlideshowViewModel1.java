package com.example.choutidemo.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel1 extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel1() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}