package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SendDocumentInRequest {
    @SerializedName("senderUsername")
    private String senderUsername;

    @SerializedName("documentId")
    private String documentOutId;

    @SerializedName("documentOfUserId")
    private String userId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("recipients")
    private ArrayList<Recipent> recipients;

    public SendDocumentInRequest(String senderUsername, String documentOutId, String userId, String comment, ArrayList<Recipent> recipients) {
        this.senderUsername = senderUsername;
        this.documentOutId = documentOutId;
        this.userId = userId;
        this.comment = comment;
        this.recipients = recipients;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<Recipent> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<Recipent> recipients) {
        this.recipients = recipients;
    }
}
