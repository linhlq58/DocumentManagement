package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class CountItem {
    @SerializedName("Title")
    private String title;

    @SerializedName("Error")
    private boolean error;

    @SerializedName("Object")
    private int count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
