package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class UrlResponse {
    @SerializedName("Message")
    private String title;

    @SerializedName("Error")
    private boolean error;

    @SerializedName("Object")
    private String fileUrl;

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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
