package com.meanwhile.flatmates;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.support.annotation.VisibleForTesting;

import com.meanwhile.flatmates.repository.db.MyDatabase;
import com.meanwhile.flatmates.feed.FeedViewModel;
import com.meanwhile.flatmates.init.CreateGroupViewModel;
import com.meanwhile.flatmates.mates.MatesViewModel;
import com.meanwhile.flatmates.task.TaskRepository;
import com.meanwhile.flatmates.task.TaskViewModel;

import java.util.concurrent.Executors;

import static com.meanwhile.flatmates.repository.db.MyDatabase.DB_NAME;

/**
 * Created by mengujua on 07/12/17.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final TaskRepository mTasksRepository;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    MyDatabase db = Room.databaseBuilder(application,
                            MyDatabase.class, DB_NAME).build();
                    INSTANCE = new ViewModelFactory(application, new TaskRepository(db.taskDao(), db.userDao(),  Executors.newSingleThreadExecutor()));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, TaskRepository repository) {
        mApplication = application;
        mTasksRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            //noinspection unchecked
            return (T) new TaskViewModel(mTasksRepository);

        } else if (modelClass.isAssignableFrom(MatesViewModel.class)) {
            return (T) new MatesViewModel(mTasksRepository);

        } else if (modelClass.isAssignableFrom(FeedViewModel.class)){
            return (T) new FeedViewModel(mTasksRepository);
        } else if (modelClass.isAssignableFrom( CreateGroupViewModel.class)) {
            return (T) new CreateGroupViewModel(mTasksRepository);
        } else if (modelClass.isAssignableFrom(FlatViewModel.class)) {
            return (T) new FlatViewModel(mTasksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
