package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class Receiver {
    @SerializedName("ReceiveMessageId")
    private String receiveMessageId;

    @SerializedName("ReceiveMessageGuidId")
    private String receiveMessageGuidId;

    @SerializedName("EmployeeId")
    private String employeeId;

    @SerializedName("EmployeeName")
    private String employeeName;

    public String getReceiveMessageId() {
        return receiveMessageId;
    }

    public void setReceiveMessageId(String receiveMessageId) {
        this.receiveMessageId = receiveMessageId;
    }

    public String getReceiveMessageGuidId() {
        return receiveMessageGuidId;
    }

    public void setReceiveMessageGuidId(String receiveMessageGuidId) {
        this.receiveMessageGuidId = receiveMessageGuidId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
