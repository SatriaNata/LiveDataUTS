package com.example.aplikasiuntukuts.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CheeseViewModel extends AndroidViewModel{
    private CheeseRepository mRepository;

    private LiveData<List<Cheese>> mAllWords;

    public CheeseViewModel (Application application) {
        super(application);
        mRepository = new CheeseRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Cheese>> getAllWords() { return mAllWords; }

    public void insert(Cheese cheese) { mRepository.insert(cheese); }
}
