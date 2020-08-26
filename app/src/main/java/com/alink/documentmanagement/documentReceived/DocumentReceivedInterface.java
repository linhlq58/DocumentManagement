package com.alink.documentmanagement.documentReceived;

import com.alink.documentmanagement.models.DocumentIn;

import java.util.List;

public class DocumentReceivedInterface {
    interface IReceivedView {
        void onUpdateList(List<DocumentIn> listIn);
    }

    interface IReceivedFunction {
        void getListIn(String type);
    }
}
