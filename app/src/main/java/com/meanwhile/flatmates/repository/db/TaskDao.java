package com.meanwhile.flatmates.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.meanwhile.flatmates.repository.model.Task;

import java.util.List;

/**
 * Created by mengujua on 06/12/17.
 */
@Dao
public interface TaskDao {

    //insert task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTask(Task task);

    //update task
    @Update
    public void updateTasks(Task... tasks);

    @Update
    public void updateTask(Task task);

    //query tasks
    @Query("SELECT * FROM task WHERE owner_id IS NULL")
    public LiveData<List<Task>> loadPendingTask();

    @Query("SELECT user.name AS username, task.name AS taskName, task.estimation AS estimation " +
            "FROM task, user WHERE task.owner_id = user.email ORDER BY task.doneDate DESC")
    LiveData<List<UserTask>> loadCompletedTasks();

}