package com.alink.documentmanagement.documentSent;

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

public class DocumentSentFragment extends BaseFragment {
    public TabLayout tabs;
    public ViewPager pager;

    public SentWaitingFragment sentWaitingFragment;
    public SentPublishedFragment sentPublishedFragment;
    public SentCompleteFragment sentCompleteFragment;

    public static DocumentSentFragment createInstance() {

        Bundle args = new Bundle();

        DocumentSentFragment fragment = new DocumentSentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_document_sent;
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
        sentWaitingFragment = SentWaitingFragment.createInstance();
        sentPublishedFragment = SentPublishedFragment.createInstance();
        sentCompleteFragment = SentCompleteFragment.createInstance();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(sentWaitingFragment);
        listFragment.add(sentPublishedFragment);
        listFragment.add(sentCompleteFragment);

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
            tab2.setCustomView(createTabView(getResources().getString(R.string.published)));
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
