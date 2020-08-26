package com.alink.documentmanagement;

import android.app.Application;
import android.content.SharedPreferences;

import com.alink.documentmanagement.appConfig.AppConfig;

public class MyApplication extends Application {
    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences(AppConfig.USER_DATA, MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
