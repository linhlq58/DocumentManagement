package com.alink.documentmanagement.message.outbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.detail.messageDetail.DetailMessageActivity;
import com.alink.documentmanagement.message.ListSendMessageAdapter;
import com.alink.documentmanagement.models.SendMessage;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class OutboxFragment extends BaseFragment implements OutboxInterface.IOutboxView {
    public RecyclerView recyclerView;
    public ListSendMessageAdapter adapter;
    public ArrayList<SendMessage> listMessage;
    public TextView tvEmpty;

    public SwipeRefreshLayout swipeRefreshLayout;

    public OutboxPresenter presenter = new OutboxPresenter(this);

    public static OutboxFragment createInstance() {

        Bundle args = new Bundle();

        OutboxFragment fragment = new OutboxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_inbox_outbox;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.layout_refresh);
        tvEmpty = rootView.findViewById(R.id.tv_empty);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listMessage = new ArrayList<>();

        //presenter.getListOut(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));

        adapter = new ListSendMessageAdapter(getActivity(), listMessage, new ListSendMessageAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailMessageActivity.class);
                intent.putExtra(AppConfig.MESSAGE_ID, listMessage.get(position).getSendMessageId());
                intent.putExtra(AppConfig.MESSAGE_TYPE, 1);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListOut(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
            }
        });
    }

    @Override
    public void onUpdateList(List<SendMessage> listOut) {
        if (listOut.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        listMessage.clear();
        listMessage.addAll(listOut);
        adapter.notifyDataSetChanged();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getListOut(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
    }
}
