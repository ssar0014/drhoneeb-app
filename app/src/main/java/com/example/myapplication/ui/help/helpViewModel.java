package com.example.myapplication.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class helpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public helpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is help fragment (It contains manual)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}