package com.meanwhile.flatmates.repository.db;

/**
 * Created by mengujua on 08/12/17.
 */

public class UserTask {

    private String username;

    private String taskName;

    private float estimation;

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public float getEstimation() {
        return estimation;
    }

    public void setEstimation(float estimation) {
        this.estimation = estimation;
    }
}
