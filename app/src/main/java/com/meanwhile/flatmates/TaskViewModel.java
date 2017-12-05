package com.meanwhile.flatmates;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by mengujua on 12/5/17.
 */

public class TaskViewModel extends ViewModel {

    private LiveData<List<String>> mPendingTasks;

    public void init(){

    }

    public LiveData<List<String>> getPendingTasks(){
        return mPendingTasks;
    }
}
