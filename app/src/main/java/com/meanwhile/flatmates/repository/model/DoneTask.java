package com.meanwhile.flatmates.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * A task that has been done
 */
@Entity
public class DoneTask {

    @PrimaryKey(autoGenerate = true)
    private int id;
}
