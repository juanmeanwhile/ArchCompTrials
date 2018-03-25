package com.meanwhile.flatmates.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.meanwhile.flatmates.repository.db.UserTask;
import com.meanwhile.flatmates.task.TaskRepository;

import java.util.List;

/**
 * Created by mengujua on 08/12/17.
 */

public class FeedViewModel extends ViewModel{

    private final TaskRepository mTaskRepo;

    public FeedViewModel(TaskRepository taskRepository) {
        mTaskRepo = taskRepository;
    }

    public void init(){

    }

    public LiveData<List<UserTask>> getCompletedTasks(){
        return mTaskRepo.getCompletedTasks();
    }
}
