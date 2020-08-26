package com.alink.documentmanagement.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.alink.documentmanagement.R;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog_loading);
    }
}
