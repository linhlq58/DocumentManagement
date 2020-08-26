package com.alink.documentmanagement.documentSent;

import com.alink.documentmanagement.models.DocumentOut;

import java.util.List;

public class DocumentSentInterface {
    interface ISentView {
        void onUpdateList(List<DocumentOut> listOut);
    }

    interface ISentFunction {
        void getListOut(String type);
    }
}
