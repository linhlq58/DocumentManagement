package com.alink.documentmanagement.message.inbox;

import com.alink.documentmanagement.models.DataWrap;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxPresenter implements InboxInterface.IInboxFunction {
    private InboxInterface.IInboxView iInboxView;
    private ApiService apiService;

    public InboxPresenter(InboxInterface.IInboxView iInboxView) {
        this.iInboxView = iInboxView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListInbox(String userName) {
        Call<DataWrap<ReceiveMessage>> call = apiService.getReceiveMessages(userName);
        call.enqueue(new Callback<DataWrap<ReceiveMessage>>() {
            @Override
            public void onResponse(Call<DataWrap<ReceiveMessage>> call, Response<DataWrap<ReceiveMessage>> response) {
                if (response.body() != null) {
                    iInboxView.onUpdateList(response.body().getObject());
                }
            }

            @Override
            public void onFailure(Call<DataWrap<ReceiveMessage>> call, Throwable t) {

            }
        });
    }
}
