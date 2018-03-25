package com.meanwhile.flatmates.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * A task. It is considered ready to be used when it has a value in its estimation field. This value is the
 * average value between the the estimations made by the users
 */

@Entity(foreignKeys = {@ForeignKey(entity = Flat.class,
        parentColumns = "id",
        childColumns = "flat_id")})
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "flat_id")
    public String flatId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "estimation")
    private float estimation;

    /**
     * Has value when the task has been completed by someone
     */
    @ColumnInfo(name = "owner_id")
    public String ownerId;

    public Date doneDate;

    @Ignore
    private List<Estimation> estimations;

    public Task(String name) {
        this.name = name;
        this.estimation = 0;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        name = in.readString();
        estimation = in.readFloat();
        ownerId = in.readString();
        doneDate = (Date) in.readSerializable();

    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getEstimation() {
        return estimation;
    }

    public void setEstimation(float estimation) {
        this.estimation = estimation;
    }

    public List<Estimation> getEstimations() {
        return estimations;
    }

    public Task setEstimations(List<Estimation> estimations) {
        this.estimations = estimations;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(estimation);
        parcel.writeString(ownerId);
        parcel.writeSerializable(doneDate);
    }
}
