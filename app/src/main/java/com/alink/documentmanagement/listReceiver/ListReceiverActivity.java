package com.alink.documentmanagement.listReceiver;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.Employee;
import com.alink.documentmanagement.models.Recipent;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListReceiverActivity extends AppCompatActivity implements ListReceiverInterface.IReceiverView, View.OnClickListener {
    private RelativeLayout btnBack, btnFinish;
    private EditText editSearch;

    private RecyclerView rcvReceiver;
    private ListReceiverAdapter adapter;
    private ArrayList<Employee> listEmployee;
    private ArrayList<String> listChecked;

    private ListReceiverPresenter presenter = new ListReceiverPresenter(this);

    private boolean isFromDocument;
    private boolean isDocumentIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        rcvReceiver = findViewById(R.id.rcv_receiver);
        editSearch = findViewById(R.id.edit_search);
        btnBack = findViewById(R.id.btn_back);
        btnFinish = findViewById(R.id.btn_finish);

        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        initData();
    }

    private void initData() {
        isFromDocument = getIntent().getBooleanExtra(AppConfig.IS_FROM_DOCUMENT, false);
        isDocumentIn = getIntent().getBooleanExtra(AppConfig.IS_DOCUMENT_IN, false);

        listEmployee = new ArrayList<>();
        listChecked = new ArrayList<>();

        if (isFromDocument) {
            if (isDocumentIn) {
                presenter.getListEmployeeDocumentIns(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
            } else {
                presenter.getListEmployeeDocumentOuts(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
            }
        } else {
            presenter.getListEmployee(false);
        }

        adapter = new ListReceiverAdapter(this, listEmployee, isDocumentIn);
        rcvReceiver.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvReceiver.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onUpdateList(List<Employee> listEmployee) {
        this.listEmployee.addAll(listEmployee);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_finish:
                Intent resultIntent = new Intent();

                if (isDocumentIn) {
                    ArrayList<Recipent> listRecipent = new ArrayList<>();

                    if (listEmployee.size() > 0) {
                        for (int i = 0; i < listEmployee.size(); i++) {
                            if (listEmployee.get(i).getAction() != 0) {
                                Recipent recipent = new Recipent(listEmployee.get(i).getEmployeeId(), listEmployee.get(i).getAction());
                                listRecipent.add(recipent);
                            }
                        }
                    }

                    resultIntent.putExtra(AppConfig.LIST_RECEIVER, listRecipent);
                } else {
                    listChecked.clear();
                    if (listEmployee.size() > 0) {
                        for (int i = 0; i < listEmployee.size(); i++) {
                            if (listEmployee.get(i).isChecked()) {
                                listChecked.add(listEmployee.get(i).getEmployeeId());
                            }
                        }
                    }

                    resultIntent.putStringArrayListExtra(AppConfig.LIST_RECEIVER, listChecked);
                }

                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }
    }
}
