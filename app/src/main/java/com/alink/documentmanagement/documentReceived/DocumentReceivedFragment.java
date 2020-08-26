package com.alink.documentmanagement.documentReceived;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;
import com.alink.documentmanagement.main.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class DocumentReceivedFragment extends BaseFragment {
    public TabLayout tabs;
    public ViewPager pager;

    public ReceivedWaitingFragment receivedWaitingFragment;
    public ReceivedCooperateFragment receivedCooperateFragment;
    public ReceivedCompleteFragment receivedCompleteFragment;

    public static DocumentReceivedFragment createInstance() {

        Bundle args = new Bundle();

        DocumentReceivedFragment fragment = new DocumentReceivedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_document_received;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        tabs = rootView.findViewById(R.id.tabs);
        pager = rootView.findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setupTabLayout();
    }

    private void setupTabLayout() {
        receivedWaitingFragment = ReceivedWaitingFragment.createInstance();
        receivedCooperateFragment = ReceivedCooperateFragment.createInstance();
        receivedCompleteFragment = ReceivedCompleteFragment.createInstance();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(receivedWaitingFragment);
        listFragment.add(receivedCooperateFragment);
        listFragment.add(receivedCompleteFragment);

        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getChildFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        if (tab1 != null) {
            tab1.setCustomView(createTabView(getResources().getString(R.string.waiting)));
        }
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        if (tab2 != null) {
            tab2.setCustomView(createTabView(getResources().getString(R.string.cooperate)));
        }
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        if (tab3 != null) {
            tab3.setCustomView(createTabView(getResources().getString(R.string.complete)));
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
}
