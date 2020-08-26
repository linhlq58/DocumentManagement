package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("userName")
    private String userName;

    @SerializedName("password")
    private String password;

    @SerializedName("remember")
    private String remember;

    @SerializedName("deviceToken")
    private String deviceToken;

    public LoginRequest(String userName, String password, String remember, String deviceToken) {
        this.userName = userName;
        this.password = password;
        this.remember = remember;
        this.deviceToken = deviceToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
