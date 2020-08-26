package com.alink.documentmanagement.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alink.documentmanagement.createNewMessage.CreateNewMessageActivity;
import com.alink.documentmanagement.message.inbox.InboxFragment;
import com.alink.documentmanagement.message.outbox.OutboxFragment;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;
import com.alink.documentmanagement.main.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MessageFragment extends BaseFragment implements View.OnClickListener {
    public TabLayout tabs;
    public ViewPager pager;

    public InboxFragment inboxFragment;
    public OutboxFragment outboxFragment;

    public RelativeLayout btnCreate;

    public static MessageFragment createInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        tabs = rootView.findViewById(R.id.tabs);
        pager = rootView.findViewById(R.id.pager);
        btnCreate = rootView.findViewById(R.id.btn_create);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setupTabLayout();
        btnCreate.setOnClickListener(this);
    }

    private void setupTabLayout() {
        inboxFragment = InboxFragment.createInstance();
        outboxFragment = OutboxFragment.createInstance();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(inboxFragment);
        listFragment.add(outboxFragment);

        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getChildFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        if (tab1 != null) {
            tab1.setCustomView(createTabView(getResources().getString(R.string.inbox)));
        }
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        if (tab2 != null) {
            tab2.setCustomView(createTabView(getResources().getString(R.string.outbox)));
        }

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabView(String name) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tabs_document_layout, null);

        TextView tabsName = itemView.findViewById(R.id.tabs_text);
        tabsName.setText(name);

        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                startActivity(CreateNewMessageActivity.class);
                break;
        }
    }
}
