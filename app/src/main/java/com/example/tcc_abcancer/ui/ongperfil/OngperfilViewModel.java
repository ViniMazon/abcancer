package com.example.tcc_abcancer.ui.ongperfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OngperfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OngperfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}