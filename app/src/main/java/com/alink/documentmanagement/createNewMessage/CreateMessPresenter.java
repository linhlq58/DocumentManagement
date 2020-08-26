package com.alink.documentmanagement.createNewMessage;

import android.content.Context;
import android.net.Uri;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.SendCommentResponse;
import com.alink.documentmanagement.models.SendMessageRequest;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;
import com.alink.documentmanagement.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMessPresenter implements CreateMessInterface.ICreateMessFunction {
    private CreateMessInterface.ICreateMessView iCreateMessView;
    private ApiService apiService;

    public CreateMessPresenter(CreateMessInterface.ICreateMessView iCreateMessView) {
        this.iCreateMessView = iCreateMessView;
        apiService =  RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void sendMessage(String title, String body, ArrayList<String> listRecipients, final boolean hasAttachment) {
        Call<SendCommentResponse> call = apiService.sendMessage(new SendMessageRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), title, body, listRecipients));
        call.enqueue(new Callback<SendCommentResponse>() {
            @Override
            public void onResponse(Call<SendCommentResponse> call, Response<SendCommentResponse> response) {
                if (response.body() != null) {
                    SendCommentResponse commentResponse = response.body();
                    if (!commentResponse.isError()) {
                        if (!hasAttachment) {
                            iCreateMessView.onSendSuccess(commentResponse.getTitle());
                        } else {
                            iCreateMessView.onStartUploadFile(commentResponse.getId());
                        }

                    } else {
                        iCreateMessView.onSendFailed(commentResponse.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<SendCommentResponse> call, Throwable t) {
                iCreateMessView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void uploadFile(Context context, Uri uri, String targetId) {
        if (uri == null) return;

        File file = new File(FileUtils.getRealPath(context, uri));
        // Khởi tạo RequestBody từ file đã được chọn
        RequestBody requestBody = RequestBody.create(
                MediaType.parse(context.getContentResolver().getType(uri)),
                file);

        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestBody);

        Call<ResponseBody> call = apiService.onFileUpload(targetId, filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response == null || response.body() == null) {
                    iCreateMessView.onSendFailed("Failed");
                    return;
                }
                try {
                    String responseUrl = response.body().string();
                    iCreateMessView.onSendSuccess("Upload file thành công");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iCreateMessView.onSendFailed(t.getLocalizedMessage());
            }
        });
    }
}
