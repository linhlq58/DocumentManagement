package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SendDocumentOutRequest {
    @SerializedName("senderUsername")
    private String senderUsername;

    @SerializedName("documentOutId")
    private String documentOutId;

    @SerializedName("checkSign")
    private boolean checkSign;

    @SerializedName("comment")
    private String comment;

    @SerializedName("recipients")
    private ArrayList<String> recipients;

    public SendDocumentOutRequest(String senderUsername, String documentOutId, boolean checkSign, String comment, ArrayList<String> recipients) {
        this.senderUsername = senderUsername;
        this.documentOutId = documentOutId;
        this.checkSign = checkSign;
        this.comment = comment;
        this.recipients = recipients;
    }

    public SendDocumentOutRequest(String senderUsername, String documentOutId, boolean checkSign, String comment) {
        this.senderUsername = senderUsername;
        this.documentOutId = documentOutId;
        this.checkSign = checkSign;
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

    public boolean isCheckSign() {
        return checkSign;
    }

    public void setCheckSign(boolean checkSign) {
        this.checkSign = checkSign;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }
}
