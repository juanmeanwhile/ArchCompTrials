package com.meanwhile.flatmates;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.task.TaskRepository;

import java.util.List;

/**
 * Created by mengujua on 10/12/17.
 */

class FlatViewModel extends ViewModel{

    private TaskRepository mTaskRepo;

    public FlatViewModel(TaskRepository taskRepository) {
        mTaskRepo = taskRepository;
    }

    public void init(){

    }

    public LiveData<List<Flat>> getFlats(){
        return mTaskRepo.getFlats();
    }

    public void onGroupCreated(long flatId) {
    }
}
