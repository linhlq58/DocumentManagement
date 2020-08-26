package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class SendMessage {
    @SerializedName("SendMessageId")
    private String sendMessageId;

    @SerializedName("SendMessageGuid")
    private String sendMessageGuid;

    @SerializedName("Title")
    private String title;

    @SerializedName("Body")
    private String body;

    @SerializedName("CreatedDate")
    private String createdDate;

    @SerializedName("AttachmentsCount")
    private int attachmentsCount;

    public String getSendMessageId() {
        return sendMessageId;
    }

    public void setSendMessageId(String sendMessageId) {
        this.sendMessageId = sendMessageId;
    }

    public String getSendMessageGuid() {
        return sendMessageGuid;
    }

    public void setSendMessageGuid(String sendMessageGuid) {
        this.sendMessageGuid = sendMessageGuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
