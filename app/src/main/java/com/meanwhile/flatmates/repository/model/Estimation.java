package com.meanwhile.flatmates.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Estimation of a task from a User
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "email",
                childColumns = "user_id"),
        @ForeignKey(entity = Task.class,
                parentColumns = "id",
                childColumns = "task_id")})
public class Estimation {

    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "task_id")
    public String taskId;

    @ColumnInfo(name = "value")
    public float value;


}
