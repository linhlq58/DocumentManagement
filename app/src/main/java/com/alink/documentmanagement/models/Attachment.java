package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class Attachment {
    @SerializedName("Id")
    private String id;

    @SerializedName("MessageAttachmentId")
    private String messageAttachmentId;

    @SerializedName("FileName")
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageAttachmentId() {
        return messageAttachmentId;
    }

    public void setMessageAttachmentId(String messageAttachmentId) {
        this.messageAttachmentId = messageAttachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
