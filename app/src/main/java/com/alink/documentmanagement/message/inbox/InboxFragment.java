package com.alink.documentmanagement.message.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.detail.messageDetail.DetailMessageActivity;
import com.alink.documentmanagement.message.ListReceiveMessageAdapter;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class InboxFragment extends BaseFragment implements InboxInterface.IInboxView {
    public RecyclerView recyclerView;
    public ListReceiveMessageAdapter adapter;
    public ArrayList<ReceiveMessage> listMessage;
    public TextView tvEmpty;

    public SwipeRefreshLayout swipeRefreshLayout;

    public InboxPresenter presenter = new InboxPresenter(this);

    public static InboxFragment createInstance() {

        Bundle args = new Bundle();

        InboxFragment fragment = new InboxFragment();
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

        //presenter.getListInbox(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));

        adapter = new ListReceiveMessageAdapter(getActivity(), listMessage, new ListReceiveMessageAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailMessageActivity.class);
                intent.putExtra(AppConfig.MESSAGE_ID, listMessage.get(position).getReceiveMessageId());
                intent.putExtra(AppConfig.MESSAGE_TYPE, 0);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListInbox(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
            }
        });
    }

    @Override
    public void onUpdateList(List<ReceiveMessage> listOut) {
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
        presenter.getListInbox(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
    }
}
