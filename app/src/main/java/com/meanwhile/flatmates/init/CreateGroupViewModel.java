package com.meanwhile.flatmates.init;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.meanwhile.flatmates.repository.Resource;
import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.repository.model.User;
import com.meanwhile.flatmates.task.TaskRepository;

import java.util.List;

/**
 * Created by mengujua on 10/12/17.
 */

public class CreateGroupViewModel extends ViewModel {

    private TaskRepository mTaskRepo;

    public CreateGroupViewModel(TaskRepository taskRepository) {
        mTaskRepo = taskRepository;
    }

    public void init(){
    }

    public LiveData<List<Flat>> getGroups(){
        return mTaskRepo.getFlats();
    }

    public LiveData<Resource<Flat>> onCreateGroup(String groupName, List<User> userList) {
        Flat flat = new Flat();
        flat.setName(groupName);
        return mTaskRepo.insertGroup(flat, userList);
    }
}
