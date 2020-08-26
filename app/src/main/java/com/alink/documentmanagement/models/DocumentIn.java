package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class DocumentIn {
    @SerializedName("DocumentInId")
    private String id;

    @SerializedName("DocumentInOfUserId")
    private String userId;

    @SerializedName("DocumentInGuidId")
    private String guiId;

    @SerializedName("Title")
    private String title;

    @SerializedName("DocumentInNumber")
    private String number;

    @SerializedName("IsDeleted")
    private boolean isDelete;

    @SerializedName("ReceivedDate")
    private String receivedDate;

    @SerializedName("UrgencyId")
    private String urgencyId;

    @SerializedName("UrgencyIdName")
    private String urgencyIdName;

    @SerializedName("CreatedDate")
    private String createdDate;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("ModifiedBy")
    private String modifiedBy;

    @SerializedName("ModifiedDate")
    private String modifiedDate;

    @SerializedName("Content")
    private String content;

    @SerializedName("CommentsCount")
    private int commentsCount;

    @SerializedName("AttachmentsCount")
    private int attachmentsCount;

    public DocumentIn() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuiId() {
        return guiId;
    }

    public void setGuiId(String guiId) {
        this.guiId = guiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getUrgencyId() {
        return urgencyId;
    }

    public void setUrgencyId(String urgencyId) {
        this.urgencyId = urgencyId;
    }

    public String getUrgencyIdName() {
        return urgencyIdName;
    }

    public void setUrgencyIdName(String urgencyIdName) {
        this.urgencyIdName = urgencyIdName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }
}
