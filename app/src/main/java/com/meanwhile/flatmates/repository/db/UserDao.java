package com.meanwhile.flatmates.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.repository.model.User;

import java.util.List;

/**
 * Created by mengujua on 08/12/17.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Query("SELECT user.name AS username, user.email AS userKey "
           + ", (SELECT total(task.estimation) FROM task, user WHERE task.owner_id = user.email ) AS points "
            + "FROM user ")
    LiveData<List<UserStats>> getUserStats();

    @Query("SELECT * FROM flat")
    LiveData<List<Flat>> getFlats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroup(Flat flat);

    @Query("SELECT * FROM flat WHERE flat.id = :flatId LIMIT 1")
    Flat getFlat(long flatId);
}
