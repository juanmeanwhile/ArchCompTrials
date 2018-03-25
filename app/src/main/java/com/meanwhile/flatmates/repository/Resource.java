package com.meanwhile.flatmates.repository;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//a generic class that describes a data with a status
public class Resource<T> {

    // Define the list of accepted constants and declare the NavigationMode annotation
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SUCCESS, ERROR, LOADING})
    public @interface Status {}

    // Declare the constants
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int LOADING = 2;

    @NonNull
    @Status
    public final int status;
    @Nullable public final T data;
    @Nullable public final String message;
    private Resource(@NonNull @Status int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}