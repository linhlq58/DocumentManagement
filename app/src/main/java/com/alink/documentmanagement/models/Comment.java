package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("CommentInId")
    private String commentInId;

    @SerializedName("CommentInGuiId")
    private String commentInGuiId;

    @SerializedName("DocumentInGuidId")
    private String documentInGuidId;

    @SerializedName("DocumentInId")
    private String documentInId;

    @SerializedName("LoginName")
    private String loginName;

    @SerializedName("EmployeeId")
    private String employeeId;

    @SerializedName("EmployeeName")
    private String employeeName;

    @SerializedName("CreatedDate")
    private String createdDate;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("ModifiedDate")
    private String modifiedDate;

    @SerializedName("ModifiedBy")
    private String modifiedBy;

    @SerializedName("Status")
    private String status;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("Body")
    private String body;

    public String getCommentInId() {
        return commentInId;
    }

    public void setCommentInId(String commentInId) {
        this.commentInId = commentInId;
    }

    public String getCommentInGuiId() {
        return commentInGuiId;
    }

    public void setCommentInGuiId(String commentInGuiId) {
        this.commentInGuiId = commentInGuiId;
    }

    public String getDocumentInGuidId() {
        return documentInGuidId;
    }

    public void setDocumentInGuidId(String documentInGuidId) {
        this.documentInGuidId = documentInGuidId;
    }

    public String getDocumentInId() {
        return documentInId;
    }

    public void setDocumentInId(String documentInId) {
        this.documentInId = documentInId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
