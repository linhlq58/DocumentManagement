package com.alink.documentmanagement.message.inbox;

import com.alink.documentmanagement.models.ReceiveMessage;

import java.util.List;

public class InboxInterface {
    interface IInboxView {
        void onUpdateList(List<ReceiveMessage> listOut);
    }

    interface IInboxFunction {
        void getListInbox(String userName);
    }
}
