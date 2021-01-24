package com.country.countries;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private final MyRepo repository ;


    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepo(application);

    }




    public MutableLiveData<ArrayList<Countries>> loadData() {
        return repository.callAPI();
    }
}
