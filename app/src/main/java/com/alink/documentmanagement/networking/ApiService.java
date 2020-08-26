package com.alink.documentmanagement.networking;

import com.alink.documentmanagement.models.Calendar;
import com.alink.documentmanagement.models.CheckPermission;
import com.alink.documentmanagement.models.Comment;
import com.alink.documentmanagement.models.CountItem;
import com.alink.documentmanagement.models.DataWrap;
import com.alink.documentmanagement.models.DataWrapWithObject;
import com.alink.documentmanagement.models.DataWrapWithObjectList;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.models.Employee;
import com.alink.documentmanagement.models.LoginRequest;
import com.alink.documentmanagement.models.LogoutRequest;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.models.SendCommentRequest;
import com.alink.documentmanagement.models.SendCommentResponse;
import com.alink.documentmanagement.models.SendDocumentInRequest;
import com.alink.documentmanagement.models.SendDocumentOutRequest;
import com.alink.documentmanagement.models.SendMessage;
import com.alink.documentmanagement.models.SendMessageRequest;
import com.alink.documentmanagement.models.SwitchUrlResponse;
import com.alink.documentmanagement.models.UrlResponse;
import com.alink.documentmanagement.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("MobileApi/Login")
    Call<DataWrapWithObject<User>> login(@Body LoginRequest loginRequest);

    @POST("MobileApi/Logout")
    Call<DataWrapWithObject<User>> logout(@Body LogoutRequest logoutRequest);

    @GET("Employees/GetByUserName")
    Call<User> getUserInfo(@Query("username") String userName);

    @GET("Employees/GetAll")
    Call<List<Employee>> getListEmployee(@Query("localOnly") boolean isLocalOnly);

    @GET("DocumentIns/GetRecipientList")
    Call<List<Employee>> getListEmployeeDocumentIns(@Query("username") String userName);

    @GET("DocumentOuts/GetRecipientList")
    Call<List<Employee>> getListEmployeeDocumentOuts(@Query("username") String userName);

    @GET("DocumentIns/GetDocumentIns")
    Call<DataWrap<DocumentIn>> getDocumentsIn(@Query("user") String userName, @Query("key") String documentType);

    @GET("DocumentOuts/GetDocumentOuts")
    Call<List<DocumentOut>> getDocumentsOut(@Query("user") String userName, @Query("key") String documentType);

    @GET("SendMessages/GetList")
    Call<DataWrap<SendMessage>> getSendMessages(@Query("u") String userName);

    @GET("ReceiveMessages/GetList")
    Call<DataWrap<ReceiveMessage>> getReceiveMessages(@Query("u") String userName);

    @GET("MobileApi/CalendarGetOrgCalendar")
    Call<List<Calendar>> getAllCalendar();

    @GET("DocumentIns/GetItem/{id}")
    Call<DataWrapWithObject<DocumentIn>> getDocumentInById(@Path("id") String id);

    @GET("DocumentOuts/GetItem/{id}")
    Call<DataWrapWithObject<DocumentOut>> getDocumentOutById(@Path("id") String id);

    @GET("DocumentIns/GetDocumentInComments/{id}")
    Call<List<Comment>> getListCommentInById(@Path("id") String id);

    @GET("DocumentOuts/GetDocumentOutComments1/{id}")
    Call<List<Comment>> getListCommentOutById(@Path("id") String id);

    @POST("DocumentOuts/AddCommentApi")
    Call<SendCommentResponse> sendComment(@Body SendCommentRequest sendCommentRequest);

    @POST("SendMessages/SendMessageApi")
    Call<SendCommentResponse> sendMessage(@Body SendMessageRequest sendMessageRequest);

    @POST("DocumentOuts/SendDocumentApi")
    Call<SendCommentResponse> sendDocument(@Body SendDocumentOutRequest sendDocumentOutRequest);

    @POST("DocumentIns/TransferDocument")
    Call<SendCommentResponse> transferDocumentIn(@Body SendDocumentInRequest sendDocumentInRequest);

    @GET("ReceiveMessages/GetCount")
    Call<CountItem> getMessageCount(@Query("u") String userName, @Query("unread") boolean unread);

    @GET("DocumentIns/GetCount")
    Call<CountItem> getDocumentCount(@Query("user") String userName, @Query("key") String documentType, @Query("unread") boolean unread);

    @GET("DocumentOuts/GetCount")
    Call<CountItem> getDocumentOutCount(@Query("user") String userName, @Query("key") String documentType, @Query("unread") boolean unread);

    @GET("ReceiveMessages/GetById/{id}")
    Call<DataWrapWithObject<ReceiveMessage>> getReceiveMessageById(@Path("id") String id);

    @GET("SendMessages/GetById/{id}")
    Call<DataWrapWithObjectList<SendMessage>> getSendMessageById(@Path("id") String id);

    @GET("MobileApi/CheckQuyenChuyenPhatHanh")
    Call<CheckPermission> checkPermisson(@Query("userName") String userName);

    @POST("DocumentOuts/ChuyenPhatHanhApi")
    Call<CheckPermission> chuyenPhatHanh(@Body SendDocumentOutRequest sendDocumentOutRequest);

    @GET("MobileApi/GetAttachmentUrl")
    Call<UrlResponse> getFileUrl(@Query("type") String fileType, @Query("fileId") String fileId);

    @Multipart
    @POST("SendMessages/UploadApi")
    Call<ResponseBody> onFileUpload(@Query("targetId") String targetId,
                                      @Part MultipartBody.Part file);

    @GET
    Call<Map<String, ServerDto>> getListBaseUrl(@Url String url);

}
