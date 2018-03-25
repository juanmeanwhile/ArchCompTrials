package com.meanwhile.flatmates.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.repository.model.Task;
import com.meanwhile.flatmates.repository.model.User;

/**
 * Created by mengujua on 07/12/17.
 */

@Database(entities = {Task.class, User.class, Flat.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class MyDatabase extends RoomDatabase {

    public static final String DB_NAME = "flatmates-database";

    public abstract TaskDao taskDao();

    public abstract UserDao userDao();

}
