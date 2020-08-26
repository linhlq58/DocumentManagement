package com.alink.documentmanagement.createNewMessage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.listReceiver.ListReceiverActivity;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.dialog.LoadingDialog;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateNewMessageActivity extends AppCompatActivity implements View.OnClickListener, CreateMessInterface.ICreateMessView {
    public RelativeLayout btnBack, btnSend;
    public EditText editTitle, editBody;
    public TextView tvChooseReceiver, tvChooseFile;
    public RecyclerView rcvReceiver;
    public ListReceiverHorizontalAdapter adapter;
    public ArrayList<String> listReceiver;
    public LoadingDialog dialog;
    public Uri currFileURI;
    public boolean hasAttachment = false;

    public CreateMessPresenter presenter = new CreateMessPresenter(this);

    public static final int REQUEST_RECEIVER = 6968;
    public static final int REQUEST_GET_FILE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_message);

        btnBack = findViewById(R.id.btn_back);
        btnSend = findViewById(R.id.btn_send);
        editTitle = findViewById(R.id.edit_title);
        editBody = findViewById(R.id.edit_body);
        tvChooseReceiver = findViewById(R.id.tv_choose_receiver);
        tvChooseFile = findViewById(R.id.tv_choose_file);
        rcvReceiver = findViewById(R.id.rcv_receiver);

        initData();
    }

    public void initData() {
        listReceiver = new ArrayList<>();

        adapter = new ListReceiverHorizontalAdapter(this, listReceiver);
        rcvReceiver.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvReceiver.setAdapter(adapter);

        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        tvChooseReceiver.setOnClickListener(this);
        tvChooseFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_choose_receiver:
                Intent i = new Intent(this, ListReceiverActivity.class);
                startActivityForResult(i, REQUEST_RECEIVER);
                break;
            case R.id.tv_choose_file:
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Set your required file type
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose file"),REQUEST_GET_FILE);
                break;
            case R.id.btn_send:
                if (listReceiver.size() > 0) {
                    dialog = new LoadingDialog(this);
                    dialog.show();

                    presenter.sendMessage(editTitle.getText().toString(), editBody.getText().toString(), listReceiver, hasAttachment);
                } else {
                    Toast.makeText(this, "Danh sách gửi trống!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RECEIVER) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> listString = data.getStringArrayListExtra(AppConfig.LIST_RECEIVER);
                listReceiver.clear();
                listReceiver.addAll(listString);
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == REQUEST_GET_FILE) {
            hasAttachment = true;

            currFileURI = data.getData();
            String path = currFileURI.getPath();

            String fileName = Utils.getFileName(CreateNewMessageActivity.this, currFileURI);
            tvChooseFile.setText(fileName);
        }
    }

    @Override
    public void onSendSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        editTitle.setText("");
        editBody.setText("");

        dialog.dismiss();
        finish();
    }

    @Override
    public void onSendFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onStartUploadFile(String targetId) {
        presenter.uploadFile(this, currFileURI, targetId);
    }
}
