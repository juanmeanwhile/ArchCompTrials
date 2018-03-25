package com.meanwhile.flatmates.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * A member of a group
 */

@Entity(foreignKeys = @ForeignKey(entity = Flat.class,
        parentColumns = "id",
        childColumns = "flat_id",
        onDelete = CASCADE))
public class User {
    @PrimaryKey
    @NonNull
    private String email;

    @ColumnInfo(name = "flat_id")
    private long flatId;

    private String name;

    public User(String name, String email) {
        this.name = name;

        //TODO set correct email!
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlatId(long flatId) {
        this.flatId = flatId;
    }

    public long getFlatId() {
        return flatId;
    }
}
