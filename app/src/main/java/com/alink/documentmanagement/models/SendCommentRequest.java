package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class SendCommentRequest {
    @SerializedName("senderUsername")
    private String senderUsername;

    @SerializedName("documentOutId")
    private String documentOutId;

    @SerializedName("comment")
    private String comment;

    public SendCommentRequest(String senderUsername, String documentOutId, String comment) {
        this.senderUsername = senderUsername;
        this.documentOutId = documentOutId;
        this.comment = comment;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getDocumentOutId() {
        return documentOutId;
    }

    public void setDocumentOutId(String documentOutId) {
        this.documentOutId = documentOutId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
