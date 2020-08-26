package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class SwitchUrlResponse {
    @SerializedName("App01")
    private ServerDto app01;

    @SerializedName("App02")
    private ServerDto app02;

    public ServerDto getApp01() {
        return app01;
    }

    public void setApp01(ServerDto app01) {
        this.app01 = app01;
    }

    public ServerDto getApp02() {
        return app02;
    }

    public void setApp02(ServerDto app02) {
        this.app02 = app02;
    }
}
