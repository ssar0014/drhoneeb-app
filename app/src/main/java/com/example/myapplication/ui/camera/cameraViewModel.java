package com.example.myapplication.ui.camera;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class cameraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public cameraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is camera fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}