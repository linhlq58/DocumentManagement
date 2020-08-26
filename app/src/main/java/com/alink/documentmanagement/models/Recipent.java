package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipent implements Serializable {
    @SerializedName("userName")
    private String userName;

    @SerializedName("action")
    private int action;

    public Recipent(String userName, int action) {
        this.userName = userName;
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
