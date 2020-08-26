package com.alink.documentmanagement.detail.documentDetail;

import android.util.Log;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.CheckPermission;
import com.alink.documentmanagement.models.Comment;
import com.alink.documentmanagement.models.DataWrapWithObject;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.models.Recipent;
import com.alink.documentmanagement.models.SendCommentRequest;
import com.alink.documentmanagement.models.SendCommentResponse;
import com.alink.documentmanagement.models.SendDocumentInRequest;
import com.alink.documentmanagement.models.SendDocumentOutRequest;
import com.alink.documentmanagement.models.UrlResponse;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailInterface.IDetailFunction {
    private DetailInterface.IDetailView iDetailView;
    private ApiService apiService;

    public DetailPresenter(DetailInterface.IDetailView iDetailView) {
        this.iDetailView = iDetailView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getListIn(String id) {
        Call<DataWrapWithObject<DocumentIn>> call = apiService.getDocumentInById(id);
        call.enqueue(new Callback<DataWrapWithObject<DocumentIn>>() {
            @Override
            public void onResponse(Call<DataWrapWithObject<DocumentIn>> call, Response<DataWrapWithObject<DocumentIn>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateDetailIn(response.body().getObject().getValue().getDocument());
                    iDetailView.onGetListAttachment(response.body().getObject().getValue().getAttachments());
                }
            }

            @Override
            public void onFailure(Call<DataWrapWithObject<DocumentIn>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListOut(String id) {
        Call<DataWrapWithObject<DocumentOut>> call = apiService.getDocumentOutById(id);
        call.enqueue(new Callback<DataWrapWithObject<DocumentOut>>() {
            @Override
            public void onResponse(Call<DataWrapWithObject<DocumentOut>> call, Response<DataWrapWithObject<DocumentOut>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateDetailOut(response.body().getObject().getValue().getDocument());
                    iDetailView.onGetListAttachment(response.body().getObject().getValue().getAttachments());
                }
            }

            @Override
            public void onFailure(Call<DataWrapWithObject<DocumentOut>> call, Throwable t) {
                Log.d("yolooo", "failed: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getListCommentIn(String id) {
        Call<List<Comment>> call = apiService.getListCommentInById(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateComment(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListCommentOut(String id) {
        Call<List<Comment>> call = apiService.getListCommentOutById(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    iDetailView.onUpdateComment(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendComment(String id, String comment) {
        Call<SendCommentResponse> call = apiService.sendComment(new SendCommentRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), id, comment));
        call.enqueue(new Callback<SendCommentResponse>() {
            @Override
            public void onResponse(Call<SendCommentResponse> call, Response<SendCommentResponse> response) {
                if (response.body() != null) {
                    SendCommentResponse commentResponse = response.body();
                    if (!commentResponse.isError()) {
                        iDetailView.onSendSuccess(commentResponse.getTitle());
                    } else {
                        iDetailView.onSendFailed(commentResponse.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<SendCommentResponse> call, Throwable t) {
                iDetailView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void sendDocument(String id, boolean checkSign, String comment, ArrayList<String> listRecipients) {
        Call<SendCommentResponse> call = apiService.sendDocument(new SendDocumentOutRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), id, checkSign, comment, listRecipients));
        call.enqueue(new Callback<SendCommentResponse>() {
            @Override
            public void onResponse(Call<SendCommentResponse> call, Response<SendCommentResponse> response) {
                if (response.body() != null) {
                    SendCommentResponse commentResponse = response.body();
                    if (!commentResponse.isError()) {
                        iDetailView.onSendDocumentSuccess(commentResponse.getTitle());
                    } else {
                        iDetailView.onSendFailed(commentResponse.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<SendCommentResponse> call, Throwable t) {
                iDetailView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void transferDocumentIn(String id, String userId, String comment, ArrayList<Recipent> listRecipients) {
        Call<SendCommentResponse> call = apiService.transferDocumentIn(new SendDocumentInRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), id, userId, comment, listRecipients));
        call.enqueue(new Callback<SendCommentResponse>() {
            @Override
            public void onResponse(Call<SendCommentResponse> call, Response<SendCommentResponse> response) {
                if (response.body() != null) {
                    SendCommentResponse commentResponse = response.body();
                    if (!commentResponse.isError()) {
                        iDetailView.onSendDocumentSuccess(commentResponse.getTitle());
                    } else {
                        iDetailView.onSendFailed(commentResponse.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<SendCommentResponse> call, Throwable t) {
                iDetailView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void checkPermission(String userName) {
        Call<CheckPermission> call = apiService.checkPermisson(userName);
        call.enqueue(new Callback<CheckPermission>() {
            @Override
            public void onResponse(Call<CheckPermission> call, Response<CheckPermission> response) {
                if (response.body() != null) {
                    iDetailView.displayPhatHanh(response.body().isHasPermission());
                }
            }

            @Override
            public void onFailure(Call<CheckPermission> call, Throwable t) {
                iDetailView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void chuyenPhatHanh(String id, String comment) {
        Call<CheckPermission> call = apiService.chuyenPhatHanh(new SendDocumentOutRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), id, false, comment));
        call.enqueue(new Callback<CheckPermission>() {
            @Override
            public void onResponse(Call<CheckPermission> call, Response<CheckPermission> response) {
                if (response.body() != null) {
                    iDetailView.onSendDocumentSuccess(response.body().getTitle());
                }
            }

            @Override
            public void onFailure(Call<CheckPermission> call, Throwable t) {
                iDetailView.onSendFailed(t.getLocalizedMessage());
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
                iDetailView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }
}
