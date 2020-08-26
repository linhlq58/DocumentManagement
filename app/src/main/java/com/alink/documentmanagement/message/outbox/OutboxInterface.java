package com.alink.documentmanagement.message.outbox;

import com.alink.documentmanagement.models.SendMessage;

import java.util.List;

public class OutboxInterface {
    interface IOutboxView {
        void onUpdateList(List<SendMessage> listOut);
    }

    interface IOutboxFunction {
        void getListOut(String userName);
    }
}
