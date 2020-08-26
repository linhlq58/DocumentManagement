package com.alink.documentmanagement.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ServerDto {
    @SerializedName("AppName")
    private String appName;

    @SerializedName("MobileApiUrl")
    private String baseUrl;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return appName;
    }
}
