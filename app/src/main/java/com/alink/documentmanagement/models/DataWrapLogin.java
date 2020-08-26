package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class DataWrapLogin {
    @SerializedName("Title")
    private String title;

    @SerializedName("Error")
    private boolean errorLogin;

    @SerializedName("Object")
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isErrorLogin() {
        return errorLogin;
    }

    public void setErrorLogin(boolean errorLogin) {
        this.errorLogin = errorLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
