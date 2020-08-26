package com.alink.documentmanagement.detail.messageDetail;

import com.alink.documentmanagement.models.DataWrapWithObject;
import com.alink.documentmanagement.models.DataWrapWithObjectList;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.models.SendMessage;
import com.alink.documentmanagement.models.UrlResponse;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMessagePresenter implements DetailMessageInterface.IDetailMsgFunction {
    private DetailMessageInterface.IDetailMsgView iDetailView;
    private ApiService apiService;

    public DetailMessagePresenter(DetailMessageInterface.IDetailMsgView iDetailView) {
        this.iDetailView = iDetailView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getMsgIn(String id) {
        Call<DataWrapWithObject<ReceiveMessage>> call = apiService.getReceiveMessageById(id);
        call.enqueue(new Callback<DataWrapWithObject<ReceiveMessage>>() {
            @Override
            public void onResponse(Call<DataWrapWithObject<ReceiveMessage>> call, Response<DataWrapWithObject<ReceiveMessage>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateMsgIn(response.body().getObject().getValue().getMessage());
                    iDetailView.onGetListAttachment(response.body().getObject().getValue().getMessageAttachments());
                }
            }

            @Override
            public void onFailure(Call<DataWrapWithObject<ReceiveMessage>> call, Throwable t) {
                iDetailView.onGetMessageFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getMsgOut(String id) {
        Call<DataWrapWithObjectList<SendMessage>> call = apiService.getSendMessageById(id);
        call.enqueue(new Callback<DataWrapWithObjectList<SendMessage>>() {
            @Override
            public void onResponse(Call<DataWrapWithObjectList<SendMessage>> call, Response<DataWrapWithObjectList<SendMessage>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateMsgOut(response.body().getObject().getValue().getMessage().get(0));
                    iDetailView.onGetListAttachment(response.body().getObject().getValue().getMessageAttachments());
                    iDetailView.onGetReceiver(response.body().getObject().getValue().getRecipients().get(0));
                }
            }

            @Override
            public void onFailure(Call<DataWrapWithObjectList<SendMessage>> call, Throwable t) {
                iDetailView.onGetMessageFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getFileUrl(String fileType, String fileId) {
        Call<UrlResponse> call = apiService.getFileUrl(fileType, fileId);
        call.enqueue(new Callback<UrlResponse>() {
            @Override
            public void onResponse(Call<UrlResponse> call, Response<UrlResponse> response) {
                if (response.body() != null) {
                    iDetailView.onGetFileUrl(response.body().getFileUrl());
                }
            }

            @Override
            public void onFailure(Call<UrlResponse> call, Throwable t) {
                iDetailView.onGetMessageFailed(t.getLocalizedMessage());
            }
        });
    }
}
