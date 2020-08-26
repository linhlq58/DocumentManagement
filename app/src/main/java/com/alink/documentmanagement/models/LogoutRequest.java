package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("userName")
    private String userName;

    @SerializedName("deviceToken")
    private String deviceToken;

    public LogoutRequest(String userName, String deviceToken) {
        this.userName = userName;
        this.deviceToken = deviceToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
