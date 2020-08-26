package com.alink.documentmanagement.message.outbox;

import com.alink.documentmanagement.models.DataWrap;
import com.alink.documentmanagement.models.SendMessage;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutboxPresenter implements OutboxInterface.IOutboxFunction {
    private OutboxInterface.IOutboxView iOutboxView;
    private ApiService apiService;

    public OutboxPresenter(OutboxInterface.IOutboxView iOutboxView) {
        this.iOutboxView = iOutboxView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListOut(String userName) {
        Call<DataWrap<SendMessage>> call = apiService.getSendMessages(userName);
        call.enqueue(new Callback<DataWrap<SendMessage>>() {
            @Override
            public void onResponse(Call<DataWrap<SendMessage>> call, Response<DataWrap<SendMessage>> response) {
                if (response.body() != null) {
                    iOutboxView.onUpdateList(response.body().getObject());
                }
            }

            @Override
            public void onFailure(Call<DataWrap<SendMessage>> call, Throwable t) {

            }
        });
    }
}
