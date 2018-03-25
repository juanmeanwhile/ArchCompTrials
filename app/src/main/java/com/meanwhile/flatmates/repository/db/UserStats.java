package com.meanwhile.flatmates.repository.db;

/**
 * Created by mengujua on 08/12/17.
 */

public class UserStats {

    private String userKey;

    private String username;

    private float points;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}
