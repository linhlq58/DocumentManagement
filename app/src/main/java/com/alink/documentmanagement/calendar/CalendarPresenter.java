package com.alink.documentmanagement.calendar;

import com.alink.documentmanagement.models.Calendar;
import com.alink.documentmanagement.models.UrlResponse;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarPresenter implements CalendarInterface.ICalendarFunction {
    private CalendarInterface.ICalendarView iCalendarView;
    private ApiService apiService;

    public CalendarPresenter(CalendarInterface.ICalendarView iCalendarView) {
        this.iCalendarView = iCalendarView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListCalendar() {
        Call<List<Calendar>> call = apiService.getAllCalendar();
        call.enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {
                if (response.body() != null) {
                    iCalendarView.onUpdateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {

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
                    iCalendarView.onGetFileUrl(response.body().getFileUrl());
                }
            }

            @Override
            public void onFailure(Call<UrlResponse> call, Throwable t) {
                iCalendarView.onGetFileFailed(t.getLocalizedMessage());
            }
        });
    }
}
