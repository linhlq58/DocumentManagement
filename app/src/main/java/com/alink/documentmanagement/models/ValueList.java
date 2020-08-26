package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ValueList<T> {
    @SerializedName("Document")
    private T document;

    @SerializedName("sentData")
    private T user;

    @SerializedName("Message")
    private ArrayList<T> message;

    @SerializedName("userDisplayName")
    private String userDisplayName;

    @SerializedName("Attachments")
    private ArrayList<Attachment> attachments;

    @SerializedName("Attactments")
    private ArrayList<Attachment> messageAttachments;

    @SerializedName("Comments")
    private List<Comment> comments;

    @SerializedName("Recipients")
    private ArrayList<Receiver> recipients;

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<Attachment> getMessageAttachments() {
        return messageAttachments;
    }

    public void setMessageAttachments(ArrayList<Attachment> messageAttachments) {
        this.messageAttachments = messageAttachments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public ArrayList<T> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<T> message) {
        this.message = message;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public ArrayList<Receiver> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<Receiver> recipients) {
        this.recipients = recipients;
    }
}
