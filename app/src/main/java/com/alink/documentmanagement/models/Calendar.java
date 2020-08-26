package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Calendar {
    @SerializedName("Title")
    private String title;

    @SerializedName("StartTime")
    private String startTime;

    @SerializedName("CalendarContent")
    private String content;

    @SerializedName("EndTime")
    private String endTime;

    @SerializedName("Attachments")
    private ArrayList<Attachment> attachments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }
}
