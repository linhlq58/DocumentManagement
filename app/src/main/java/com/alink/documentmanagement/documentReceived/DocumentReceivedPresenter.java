package com.alink.documentmanagement.documentReceived;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.DataWrap;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentReceivedPresenter implements DocumentReceivedInterface.IReceivedFunction {
    private DocumentReceivedInterface.IReceivedView receivedView;
    private ApiService apiService;

    public DocumentReceivedPresenter(DocumentReceivedInterface.IReceivedView receivedView) {
        this.receivedView = receivedView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListIn(String type) {
        Call<DataWrap<DocumentIn>> call = apiService.getDocumentsIn(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, "") ,type);
        call.enqueue(new Callback<DataWrap<DocumentIn>>() {
            @Override
            public void onResponse(Call<DataWrap<DocumentIn>> call, Response<DataWrap<DocumentIn>> response) {
                if (response.body() != null) {
                    receivedView.onUpdateList(response.body().getObject());
                }
            }

            @Override
            public void onFailure(Call<DataWrap<DocumentIn>> call, Throwable t) {

            }
        });
    }
}
