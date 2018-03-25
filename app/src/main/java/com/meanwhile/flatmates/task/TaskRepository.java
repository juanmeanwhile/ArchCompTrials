package com.meanwhile.flatmates.task;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.meanwhile.flatmates.repository.Resource;
import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.repository.model.Task;
import com.meanwhile.flatmates.repository.db.TaskDao;
import com.meanwhile.flatmates.repository.model.User;
import com.meanwhile.flatmates.repository.db.UserDao;
import com.meanwhile.flatmates.repository.db.UserStats;
import com.meanwhile.flatmates.repository.db.UserTask;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskRepository {

    private TaskDao taskDao;
    private UserDao userDao;
    private final Executor executor;
    private MutableLiveData<Resource<Flat>> mFlatLiveData;

    public TaskRepository(TaskDao taskDao, UserDao userDao,Executor executor) {
        this.taskDao = taskDao;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<List<Task>> getPendingTasks(){
        return taskDao.loadPendingTask();
    }

    public void insertTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }

    public void insertUser(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }

    public LiveData<List<UserStats>> getUserStats() {
        return userDao.getUserStats();
    }

    public void updateTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task);
            }
        });
    }

    public LiveData<List<UserTask>> getCompletedTasks() {
        return taskDao.loadCompletedTasks();
    }

    public LiveData<List<Flat>> getFlats() {
        return userDao.getFlats();
    }

    public LiveData<Resource<Flat>> insertGroup(final Flat flat, final List<User> users) {
        mFlatLiveData = new MutableLiveData<Resource<Flat>>();
        mFlatLiveData.setValue(Resource.<Flat>loading(null));

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //TODO Some day this should be first inserted in the server
                //And then here in the db. The ids will be populated by the server

                //Insert Group
                long id = userDao.insertGroup(flat);

                for (User user : users) {
                    user.setFlatId(id);
                }

                //insert users in the group
                userDao.insertUsers(users);

                mFlatLiveData.postValue(Resource.success(userDao.getFlat(id)));
            }
        });

        return mFlatLiveData;
    }
}
