package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class ReceiveMessage {
    @SerializedName("ReceiveMessageId")
    private String receiveMessageId;

    @SerializedName("ReceiveMessageGuidId")
    private String receiveMessageGuidId;

    @SerializedName("Title")
    private String title;

    @SerializedName("SendByEmployeeId")
    private String sendByEmployeeId;

    @SerializedName("SendByName")
    private String sendByName;

    @SerializedName("SendDepartmentName")
    private String sendDepartmentName;

    @SerializedName("Body")
    private String body;

    @SerializedName("CreatedDate")
    private String createdDate;

    @SerializedName("AttachmentsCount")
    private int attachmentsCount;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendByEmployeeId() {
        return sendByEmployeeId;
    }

    public void setSendByEmployeeId(String sendByEmployeeId) {
        this.sendByEmployeeId = sendByEmployeeId;
    }

    public String getSendByName() {
        return sendByName;
    }

    public void setSendByName(String sendByName) {
        this.sendByName = sendByName;
    }

    public String getSendDepartmentName() {
        return sendDepartmentName;
    }

    public void setSendDepartmentName(String sendDepartmentName) {
        this.sendDepartmentName = sendDepartmentName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }
}
