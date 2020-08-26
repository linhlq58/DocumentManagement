package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SendMessageRequest {
    @SerializedName("senderUsername")
    private String senderUsername;

    @SerializedName("messageTitle")
    private String messageTitle;

    @SerializedName("messageBody")
    private String messageBody;

    @SerializedName("recipients")
    private ArrayList<String> recipients;

    public SendMessageRequest(String senderUsername, String messageTitle, String messageBody, ArrayList<String> recipients) {
        this.senderUsername = senderUsername;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.recipients = recipients;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }
}
