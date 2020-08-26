package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("DeviceTokenId")
    private String deviceTokenId;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Token")
    private String token;

    @SerializedName("IsActive")
    private boolean isActive;

    @SerializedName("EmployeeName")
    private String employeeName;

    @SerializedName("DepartmentName")
    private String departmentName;

    public String getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(String deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
