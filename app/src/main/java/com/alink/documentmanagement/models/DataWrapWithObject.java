package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class DataWrapWithObject<T> {
    @SerializedName("Title")
    private String title;

    @SerializedName("error")
    private String error;

    @SerializedName("Error")
    private boolean errorLogin;

    @SerializedName("Object")
    private ObjectWrap<T> object;

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

    public boolean isErrorLogin() {
        return errorLogin;
    }

    public void setErrorLogin(boolean errorLogin) {
        this.errorLogin = errorLogin;
    }

    public ObjectWrap<T> getObject() {
        return object;
    }

    public void setObject(ObjectWrap<T> object) {
        this.object = object;
    }
}
