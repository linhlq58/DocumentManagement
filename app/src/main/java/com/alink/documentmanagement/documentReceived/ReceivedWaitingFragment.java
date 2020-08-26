package com.alink.documentmanagement.documentReceived;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.detail.documentDetail.DetailActivity;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ReceivedWaitingFragment extends BaseFragment implements DocumentReceivedInterface.IReceivedView {
    public RecyclerView recyclerView;
    public ListDocumentReceivedAdapter adapter;
    public ArrayList<DocumentIn> listDocument;
    public TextView tvEmpty;

    public SwipeRefreshLayout swipeRefreshLayout;

    public DocumentReceivedPresenter presenter = new DocumentReceivedPresenter(this);

    public static ReceivedWaitingFragment createInstance() {

        Bundle args = new Bundle();

        ReceivedWaitingFragment fragment = new ReceivedWaitingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_received_waiting;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.layout_refresh);
        tvEmpty = rootView.findViewById(R.id.tv_empty);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listDocument = new ArrayList<>();

        //presenter.getListIn(AppConfig.VB_choxuli);

        adapter = new ListDocumentReceivedAdapter(getActivity(), listDocument, new ListDocumentReceivedAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConfig.DOCUMENT_ID, listDocument.get(position).getId());
                intent.putExtra(AppConfig.DOCUMENT_TYPE, 0);
                intent.putExtra(AppConfig.DOCUMENT_USER_ID, listDocument.get(position).getUserId());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListIn(AppConfig.VB_choxuli);
            }
        });
    }

    @Override
    public void onUpdateList(List<DocumentIn> listIn) {
        if (listIn.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        listDocument.clear();
        listDocument.addAll(listIn);
        adapter.notifyDataSetChanged();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getListIn(AppConfig.VB_choxuli);
    }
}
