package com.alink.documentmanagement.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.models.Calendar;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.dialog.ListAttachmentDialog;
import com.alink.documentmanagement.dialog.LoadingDialog;
import com.alink.documentmanagement.main.BaseFragment;
import com.alink.documentmanagement.main.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CalendarFragment extends BaseFragment implements CalendarInterface.ICalendarView {
    public RecyclerView recyclerView;
    public ListCalendarAdapter adapter;
    public ArrayList<Calendar> listCalendar;

    public SwipeRefreshLayout swipeRefreshLayout;

    public CalendarPresenter presenter = new CalendarPresenter(this);

    public LoadingDialog loadingDialog;

    public static CalendarFragment createInstance() {

        Bundle args = new Bundle();

        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.layout_refresh);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listCalendar = new ArrayList<>();

        presenter.getListCalendar();

        adapter = new ListCalendarAdapter(getActivity(), listCalendar, new ListCalendarAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                ListAttachmentDialog dialog = new ListAttachmentDialog(getActivity(), listCalendar.get(position).getAttachments(), new ListAttachmentDialog.IOnOpenFile() {
                    @Override
                    public void onClickFile(Attachment attachment) {
                        loadingDialog = new LoadingDialog(getActivity());
                        loadingDialog.show();

                        String fileType = AppConfig.TYPE_CALENDAR;
                        presenter.getFileUrl(fileType, attachment.getId());
                    }
                });
                dialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListCalendar();
            }
        });
    }

    @Override
    public void onUpdateList(List<Calendar> listCalendar) {
        this.listCalendar.clear();
        this.listCalendar.addAll(listCalendar);
        adapter.notifyDataSetChanged();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onGetFileUrl(String url) {
        if (loadingDialog != null) loadingDialog.dismiss();

        Intent i = new Intent(getActivity(), WebviewActivity.class);
        i.putExtra(AppConfig.FILE_URL, url);
        startActivity(i);
    }

    @Override
    public void onGetFileFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        if (loadingDialog != null) loadingDialog.dismiss();
    }
}
