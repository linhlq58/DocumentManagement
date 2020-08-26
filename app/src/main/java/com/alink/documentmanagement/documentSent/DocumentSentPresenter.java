package com.alink.documentmanagement.documentSent;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentSentPresenter implements DocumentSentInterface.ISentFunction {
    private DocumentSentInterface.ISentView sentView;
    private ApiService apiService;

    public DocumentSentPresenter(DocumentSentInterface.ISentView sentView) {
        this.sentView = sentView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListOut(String type) {
        Call<List<DocumentOut>> call = apiService.getDocumentsOut(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), type);
        call.enqueue(new Callback<List<DocumentOut>>() {
            @Override
            public void onResponse(Call<List<DocumentOut>> call, Response<List<DocumentOut>> response) {
                if (response.body() != null) {
                    sentView.onUpdateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DocumentOut>> call, Throwable t) {

            }
        });
    }
}
