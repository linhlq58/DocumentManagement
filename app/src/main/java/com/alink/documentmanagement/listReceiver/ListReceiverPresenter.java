package com.alink.documentmanagement.listReceiver;

import com.alink.documentmanagement.models.Employee;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListReceiverPresenter implements ListReceiverInterface.IReceiverFunction {
    private ListReceiverInterface.IReceiverView iReceiverView;
    private ApiService apiService;

    public ListReceiverPresenter(ListReceiverInterface.IReceiverView iReceiverView) {
        this.iReceiverView = iReceiverView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListEmployee(boolean isLocalOnly) {
        Call<List<Employee>> call = apiService.getListEmployee(isLocalOnly);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.body() != null) {
                    iReceiverView.onUpdateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListEmployeeDocumentIns(String userName) {
        Call<List<Employee>> call = apiService.getListEmployeeDocumentIns(userName);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.body() != null) {
                    iReceiverView.onUpdateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListEmployeeDocumentOuts(String userName) {
        Call<List<Employee>> call = apiService.getListEmployeeDocumentOuts(userName);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.body() != null) {
                    iReceiverView.onUpdateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
    }
}
