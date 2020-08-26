package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class SendCommentResponse {
    @SerializedName("ID")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Error")
    private boolean error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
