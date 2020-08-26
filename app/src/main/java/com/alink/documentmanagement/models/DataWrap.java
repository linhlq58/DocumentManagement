package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataWrap<T> {
    @SerializedName("Title")
    private String title;

    @SerializedName("error")
    private String error;

    @SerializedName("Object")
    private List<T> object;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getObject() {
        return object;
    }

    public void setObject(List<T> object) {
        this.object = object;
    }
}
