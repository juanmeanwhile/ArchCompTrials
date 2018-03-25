package com.meanwhile.flatmates.mates;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.meanwhile.flatmates.repository.model.User;
import com.meanwhile.flatmates.repository.db.UserStats;
import com.meanwhile.flatmates.task.TaskRepository;

import java.util.List;

/**
 * Created by mengujua on 08/12/17.
 */

public class MatesViewModel extends ViewModel {

    private final TaskRepository mTaskRepo;

    public MatesViewModel(TaskRepository taskRepository) {
        mTaskRepo = taskRepository;
    }

    public void init(){

    }

    public LiveData<List<UserStats>> getUserStats() {
        return mTaskRepo.getUserStats();
    }

    public void onAddUser(User user) {
        mTaskRepo.insertUser(user);
    }
}
