package com.alink.documentmanagement.detail.documentDetail;

import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.models.Comment;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.models.Recipent;

import java.util.ArrayList;
import java.util.List;

public class DetailInterface {
    interface IDetailView {
        void onUpdateDetailIn(DocumentIn documentIn);

        void onUpdateDetailOut(DocumentOut documentOut);

        void onUpdateComment(List<Comment> listComment);

        void onSendSuccess(String msg);

        void onSendDocumentSuccess(String msg);

        void onSendFailed(String msg);

        void onGetListAttachment(ArrayList<Attachment> listAttachment);

        void displayPhatHanh(boolean hasPermission);

        void onGetFileUrl(String fileUrl);
    }

    interface IDetailFunction {
        void getListIn(String id);

        void getListOut(String id);

        void getListCommentIn(String id);

        void getListCommentOut(String id);

        void sendComment(String id, String comment);

        void sendDocument(String id, boolean checkSign, String comment, ArrayList<String> listRecipients);

        void transferDocumentIn(String id, String userId, String comment, ArrayList<Recipent> listRecipients);

        void checkPermission(String userName);

        void chuyenPhatHanh(String id, String comment);

        void getFileUrl(String fileType, String fileId);
    }
}
