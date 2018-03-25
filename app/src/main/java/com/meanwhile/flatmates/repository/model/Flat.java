package com.meanwhile.flatmates.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Group of users, task and done task
 */
@Entity
public class Flat {

    //TODO this will come from the server in the future
    @PrimaryKey( autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
