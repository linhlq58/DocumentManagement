package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class ObjectWrapList<T> {
    @SerializedName("ContentType")
    private String contentType;

    @SerializedName("SerializerSettings")
    private String serializerSettings;

    @SerializedName("StatusCode")
    private String statusCode;

    @SerializedName("Value")
    private ValueList<T> value;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSerializerSettings() {
        return serializerSettings;
    }

    public void setSerializerSettings(String serializerSettings) {
        this.serializerSettings = serializerSettings;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public ValueList<T> getValue() {
        return value;
    }

    public void setValue(ValueList<T> value) {
        this.value = value;
    }
}
