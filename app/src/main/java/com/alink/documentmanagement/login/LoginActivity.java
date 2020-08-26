package com.alink.documentmanagement.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.DataWrapWithObject;
import com.alink.documentmanagement.models.LoginRequest;
import com.alink.documentmanagement.models.ServerDto;
import com.alink.documentmanagement.models.SwitchUrlResponse;
import com.alink.documentmanagement.models.User;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.MainActivity;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;
import com.alink.documentmanagement.dialog.LoadingDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public ApiService apiService;
    public RelativeLayout btnLogin;
    public EditText editUsername, editPass;
    public ImageView ivBg;
    public Spinner spinnerServer;
    public LoadingDialog dialog;

    public ArrayList<ServerDto> listServer = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, "").equals("")) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        editUsername = findViewById(R.id.edit_username);
        editPass = findViewById(R.id.edit_password);
        ivBg = findViewById(R.id.iv_bg);
        spinnerServer = findViewById(R.id.spinner_server);

        getListServer();
        Glide.with(this).load(R.drawable.background).into(ivBg);

        initData();

    }

    public void initData() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        btnLogin.setOnClickListener(this);
    }

    public void setupSpinner() {
        spinnerServer.getBackground().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<ServerDto> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_status_text, listServer);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerServer.setAdapter(spinnerAdapter);

        String currentServer = MyApplication.getSharedPreferences().getString(AppConfig.CURRENT_SERVER, "http://mobile.labdieuhanh.ungdungonline.vn/");
        for (int i = 0; i < listServer.size(); i++) {
            if (currentServer.equals(listServer.get(i).getBaseUrl() + "/")) {
                spinnerServer.setSelection(i);
                break;
            }
        }
    }

    public void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Failed to get token", Toast.LENGTH_SHORT).show();
                            login("failed");
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        MyApplication.getSharedPreferences().edit().putString(AppConfig.CURRENT_TOKEN, token).apply();

                        login(token);
                    }
                });
    }

    private boolean checkIfAlreadyhavePermission() {
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                dialog = new LoadingDialog(this);
                dialog.show();
                getToken();
                break;
        }
    }

    public void getListServer() {
        apiService = (new RetrofitClient(MyApplication.getSharedPreferences().getString(AppConfig.CURRENT_SERVER, "http://mobile.labdieuhanh.ungdungonline.vn/"))).getApiService();
        Call<Map<String, ServerDto>> call = apiService.getListBaseUrl("http://api.qlvb.ungdungonline.vn/apilist");
        call.enqueue(new Callback<Map<String, ServerDto>>() {
            @Override
            public void onResponse(Call<Map<String, ServerDto>> call, Response<Map<String, ServerDto>> response) {
                if (response.body() != null) {
                    Map<String, ServerDto> serverMap = response.body();
                    for (String id : serverMap.keySet()) {
                        ServerDto serverDto = serverMap.get(id);

                        listServer.add(serverDto);
                    }

                    setupSpinner();
                }
            }

            @Override
            public void onFailure(Call<Map<String, ServerDto>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String token) {
        apiService = (new RetrofitClient(listServer.get(spinnerServer.getSelectedItemPosition()).getBaseUrl())).getApiService();
        Call<DataWrapWithObject<User>> call = apiService.login(new LoginRequest(editUsername.getText().toString(), editPass.getText().toString(), "false", token));
        call.enqueue(new Callback<DataWrapWithObject<User>>() {
            @Override
            public void onResponse(Call<DataWrapWithObject<User>> call, Response<DataWrapWithObject<User>> response) {
                if (response.body() != null) {
                    Toast.makeText(LoginActivity.this, response.body().getTitle(), Toast.LENGTH_SHORT).show();

                    if (!response.body().isErrorLogin()) {
                        User user = response.body().getObject().getValue().getUser();
                        MyApplication.getSharedPreferences().edit().putString(AppConfig.USER_NAME, user.getUserName()).apply();
                        MyApplication.getSharedPreferences().edit().putString(AppConfig.CURRENT_SERVER, listServer.get(spinnerServer.getSelectedItemPosition()).getBaseUrl() + "/").apply();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataWrapWithObject<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }
}
