package com.alink.documentmanagement.detail.messageDetail;

import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.models.Receiver;
import com.alink.documentmanagement.models.SendMessage;

import java.util.ArrayList;

public class DetailMessageInterface {
    interface IDetailMsgView {
        void onUpdateMsgIn(ReceiveMessage msg);

        void onUpdateMsgOut(SendMessage msg);

        void onGetMessageFailed(String msg);

        void onGetListAttachment(ArrayList<Attachment> listAttachment);

        void onGetFileUrl(String fileUrl);

        void onGetReceiver(Receiver receiver);
    }

    interface IDetailMsgFunction {
        void getMsgIn(String id);

        void getMsgOut(String id);

        void getFileUrl(String fileType, String fileId);
    }
}
