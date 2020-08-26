package com.alink.documentmanagement.documentSent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.detail.documentDetail.DetailActivity;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SentPublishedFragment extends BaseFragment implements DocumentSentInterface.ISentView {
    public RecyclerView recyclerView;
    public ListDocumentSentAdapter adapter;
    public ArrayList<DocumentOut> listDocument;
    public TextView tvEmpty;

    public SwipeRefreshLayout swipeRefreshLayout;

    public DocumentSentPresenter presenter = new DocumentSentPresenter(this);

    public static SentPublishedFragment createInstance() {

        Bundle args = new Bundle();

        SentPublishedFragment fragment = new SentPublishedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sent_all;
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

        //presenter.getListOut(AppConfig.VB_chophathanh);

        adapter = new ListDocumentSentAdapter(getActivity(), listDocument, new ListDocumentSentAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConfig.DOCUMENT_ID, listDocument.get(position).getId());
                intent.putExtra(AppConfig.DOCUMENT_TYPE, 1);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListOut(AppConfig.VB_chophathanh);
            }
        });
    }

    @Override
    public void onUpdateList(List<DocumentOut> listOut) {
        if (listOut.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        listDocument.clear();
        listDocument.addAll(listOut);
        adapter.notifyDataSetChanged();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getListOut(AppConfig.VB_chophathanh);
    }
}
