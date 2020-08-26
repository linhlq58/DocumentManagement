package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class CheckPermission {
    @SerializedName("Title")
    private String title;

    @SerializedName("Error")
    private boolean error;

    @SerializedName("Object")
    private boolean hasPermission;

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

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }
}
