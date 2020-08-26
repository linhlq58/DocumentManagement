package com.alink.documentmanagement.createNewMessage;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;

public class CreateMessInterface {
    interface ICreateMessView {
        void onSendSuccess(String msg);

        void onSendFailed(String msg);

        void onStartUploadFile(String targetId);
    }

    interface ICreateMessFunction {
        void sendMessage(String title, String body, ArrayList<String> listRecipients, boolean hasAttachment);

        void uploadFile(Context context, Uri uri, String targetId);
    }
}
