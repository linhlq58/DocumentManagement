package com.alink.documentmanagement.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.CountItem;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.account.AccountFragment;
import com.alink.documentmanagement.calendar.CalendarFragment;
import com.alink.documentmanagement.documentReceived.DocumentReceivedFragment;
import com.alink.documentmanagement.documentSent.DocumentSentFragment;
import com.alink.documentmanagement.message.MessageFragment;
import com.alink.documentmanagement.networking.ApiService;
import com.alink.documentmanagement.networking.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TabLayout tabs;
    public ViewPager pager;
    public DocumentReceivedFragment documentReceivedFragment;
    public DocumentSentFragment documentSentFragment;
    public MessageFragment messageFragment;
    public CalendarFragment calendarFragment;
    public AccountFragment accountFragment;

    private ApiService apiService = RetrofitClient.getInstance().getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);

        setupTabLayout();
    }

    private void setupTabLayout() {
        documentReceivedFragment = DocumentReceivedFragment.createInstance();
        documentSentFragment = DocumentSentFragment.createInstance();
        messageFragment = MessageFragment.createInstance();
        calendarFragment = CalendarFragment.createInstance();
        accountFragment = AccountFragment.createInstance();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(documentReceivedFragment);
        listFragment.add(documentSentFragment);
        listFragment.add(messageFragment);
        listFragment.add(calendarFragment);
        listFragment.add(accountFragment);

        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        //pager.setOffscreenPageLimit(4);
        pager.setOffscreenPageLimit(5);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        if (tab1 != null) {
            tab1.setCustomView(createTabView(R.drawable.tab_document_received_selector, getResources().getString(R.string.document_received)));
        }
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        if (tab2 != null) {
            tab2.setCustomView(createTabView(R.drawable.tab_document_sent_selector, getResources().getString(R.string.document_sent)));
        }
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        if (tab3 != null) {
            tab3.setCustomView(createTabView(R.drawable.tab_message_selector, getResources().getString(R.string.message)));
        }
        TabLayout.Tab tab4 = tabs.getTabAt(3);
        if (tab4 != null) {
            tab4.setCustomView(createTabView(R.drawable.tab_calendar_selector, getResources().getString(R.string.calendar)));
        }
        TabLayout.Tab tab5 = tabs.getTabAt(4);
        if (tab5 != null) {
            tab5.setCustomView(createTabView(R.drawable.tab_account_selector, getResources().getString(R.string.account)));
        }

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

                /*switch (tab.getPosition()) {
                    case 0:
                        tvTitle.setText(getResources().getString(R.string.string_leads));
                        break;
                    case 1:
                        tvTitle.setText(getResources().getString(R.string.string_customers));
                        break;
                    case 2:
                        tvTitle.setText(getResources().getString(R.string.string_labels));
                        break;
                    case 3:
                        tvTitle.setText(getResources().getString(R.string.string_tasks));
                        break;
                    case 4:
                        tvTitle.setText(getResources().getString(R.string.string_graph));
                        break;
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getMessageCount();
        getDocumentCount();
        getDocumentOutCount();
    }

    private View createTabView(int imgRes, String name) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.tabs_main_layout, null);

        ImageView tabsIcon = itemView.findViewById(R.id.tabs_icon);
        TextView tabsName = itemView.findViewById(R.id.tabs_text);
        TextView countIndicator = itemView.findViewById(R.id.tv_count_indicator);
        tabsIcon.setImageResource(imgRes);
        tabsName.setText(name);

        return itemView;
    }

    private void getMessageCount() {
        Call<CountItem> call = apiService.getMessageCount(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), true);
        call.enqueue(new Callback<CountItem>() {
            @Override
            public void onResponse(Call<CountItem> call, Response<CountItem> response) {
                if (response.body() != null) {
                    if (!response.body().isError()) {
                        int messageCount = response.body().getCount();

                        if (messageCount > 0) {
                            View tabView = tabs.getTabAt(2).getCustomView();
                            TextView countIndicator = tabView.findViewById(R.id.tv_count_indicator);
                            countIndicator.setVisibility(View.VISIBLE);
                            countIndicator.setText(messageCount + "");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CountItem> call, Throwable t) {

            }
        });
    }

    private void getDocumentCount() {
        Call<CountItem> call = apiService.getDocumentCount(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), AppConfig.VB_choxuli, true);
        call.enqueue(new Callback<CountItem>() {
            @Override
            public void onResponse(Call<CountItem> call, Response<CountItem> response) {
                if (response.body() != null) {
                    if (!response.body().isError()) {
                        int documentCount = response.body().getCount();

                        if (documentCount > 0) {
                            View tabView = tabs.getTabAt(0).getCustomView();
                            TextView countIndicator = tabView.findViewById(R.id.tv_count_indicator);
                            countIndicator.setVisibility(View.VISIBLE);
                            countIndicator.setText(documentCount + "");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CountItem> call, Throwable t) {

            }
        });
    }

    private void getDocumentOutCount() {
        Call<CountItem> call = apiService.getDocumentOutCount(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""), AppConfig.VB_choxuli, false);
        call.enqueue(new Callback<CountItem>() {
            @Override
            public void onResponse(Call<CountItem> call, Response<CountItem> response) {
                if (response.body() != null) {
                    if (!response.body().isError()) {
                        int documentCount = response.body().getCount();

                        if (documentCount > 0) {
                            View tabView = tabs.getTabAt(1).getCustomView();
                            TextView countIndicator = tabView.findViewById(R.id.tv_count_indicator);
                            countIndicator.setVisibility(View.VISIBLE);
                            countIndicator.setText(documentCount + "");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CountItem> call, Throwable t) {

            }
        });
    }
}
