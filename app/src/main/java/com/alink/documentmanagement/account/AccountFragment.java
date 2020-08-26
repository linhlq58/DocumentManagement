package com.alink.documentmanagement.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.login.LoginActivity;
import com.alink.documentmanagement.models.DataWrapWithObject;
import com.alink.documentmanagement.models.LogoutRequest;
import com.alink.documentmanagement.models.User;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends BaseFragment {
    private ApiService apiService = RetrofitClient.getInstance().getApiService();
    public TextView tvName, tvId, tvDepartment;
    public Button btnLogout;

    public static AccountFragment createInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        tvName = rootView.findViewById(R.id.tv_name);
        tvId = rootView.findViewById(R.id.tv_id);
        tvDepartment = rootView.findViewById(R.id.tv_department);
        btnLogout = rootView.findViewById(R.id.btn_logout);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getUserInfo();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void getUserInfo() {
        Call<User> call = apiService.getUserInfo(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    User user = response.body();

                    tvName.setText(user.getEmployeeName());
                    tvId.setText(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
                    tvDepartment.setText(user.getDepartmentName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void logout() {
        Call<DataWrapWithObject<User>> call = apiService.logout(new LogoutRequest(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), MyApplication.getSharedPreferences().getString(AppConfig.CURRENT_TOKEN, "")));
        call.enqueue(new Callback<DataWrapWithObject<User>>() {
            @Override
            public void onResponse(Call<DataWrapWithObject<User>> call, Response<DataWrapWithObject<User>> response) {
                MyApplication.getSharedPreferences().edit().remove(AppConfig.USER_NAME).commit();
                RetrofitClient.clearInstance();

                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);

                getActivity().finish();

                Toast.makeText(getActivity(), response.body().getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DataWrapWithObject<User>> call, Throwable t) {

            }
        });
    }
}
