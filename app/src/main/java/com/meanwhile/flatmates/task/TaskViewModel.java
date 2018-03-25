package com.meanwhile.flatmates.task;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.meanwhile.flatmates.repository.model.Task;
import com.meanwhile.flatmates.repository.db.UserStats;

import java.util.Date;
import java.util.List;

/**
 * Created by mengujua on 12/5/17.
 */

public class TaskViewModel extends ViewModel {

    private TaskRepository mTaskRepo;

    public TaskViewModel(TaskRepository taskRepository) {
        mTaskRepo = taskRepository;
    }

    public void init(){
    }

    public LiveData<List<Task>> getPendingTask(){
        return mTaskRepo.getPendingTasks();
    }

    public void addTask(Task task) {
        mTaskRepo.insertTask(task);
    }

    public void onCreateTask(String taskName) {
        Task task = new Task(taskName);
        mTaskRepo.insertTask(task);
    }

    public void onTaskEstimated(Task task) {
        mTaskRepo.updateTask(task);
    }

    //TODO change to User instead of UserStats
    public void onTaskAssigned(Task task, UserStats user) {
        task.setOwnerId(user.getUserKey());
        task.setDoneDate(new Date());
        mTaskRepo.updateTask(task);
    }
}
