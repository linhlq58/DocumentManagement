package com.alink.documentmanagement.detail.messageDetail;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.models.Receiver;
import com.alink.documentmanagement.models.SendMessage;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.dialog.ListAttachmentDialog;
import com.alink.documentmanagement.dialog.LoadingDialog;
import com.alink.documentmanagement.main.WebviewActivity;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailMessageActivity extends AppCompatActivity implements DetailMessageInterface.IDetailMsgView, View.OnClickListener {
    public DownloadManager downloadManager;
    public String messageId;
    public RelativeLayout btnBack;
    public TextView tvTitle, tvSender, tvTime, tvContent, btnOpenFile;
    public ArrayList<Attachment> listAttachment = new ArrayList<>();

    public DetailMessagePresenter presenter = new DetailMessagePresenter(this);

    public LoadingDialog loadingDialog;
    public String currentFileType;
    public int messageType;
    public BroadcastReceiver onComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_message);

        btnBack = findViewById(R.id.btn_back);
        btnOpenFile = findViewById(R.id.btn_open_file);
        tvTitle = findViewById(R.id.tv_title);
        tvSender = findViewById(R.id.tv_sender);
        tvTime = findViewById(R.id.tv_time);
        tvContent = findViewById(R.id.tv_content);

        initData();
    }

    public void initData() {
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        messageId = getIntent().getStringExtra(AppConfig.MESSAGE_ID);
        messageType = getIntent().getIntExtra(AppConfig.MESSAGE_TYPE, 0);

        if (messageType == 0) {
            presenter.getMsgIn(messageId);
        } else {
            presenter.getMsgOut(messageId);
        }

        btnBack.setOnClickListener(this);
        btnOpenFile.setOnClickListener(this);

        onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadingDialog.dismiss();

                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(DetailMessageActivity.this, "Completed with id: " + referenceId, Toast.LENGTH_SHORT).show();

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(referenceId);

                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        Utils.onOpenFile(Uri.parse("content://" + Uri.parse(uriString).getPath()), currentFileType, DetailMessageActivity.this);
                    }
                }
            }
        };

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onUpdateMsgIn(ReceiveMessage msg) {
        tvTitle.setText(msg.getTitle());
        tvSender.setText("Người gửi: " + msg.getSendByName());
        tvTime.setText("Thời gian: " + Utils.formatToDateTime(msg.getCreatedDate()));
        if (msg.getBody() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvContent.setText(Html.fromHtml(msg.getBody(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvContent.setText(Html.fromHtml(msg.getBody()));
            }
        }
    }

    @Override
    public void onUpdateMsgOut(SendMessage msg) {
        tvTitle.setText(msg.getTitle());
        tvTime.setText("Thời gian: " + Utils.formatToDateTime(msg.getCreatedDate()));
        if (msg.getBody() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvContent.setText(Html.fromHtml(msg.getBody(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvContent.setText(Html.fromHtml(msg.getBody()));
            }
        }
    }

    @Override
    public void onGetMessageFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        if (loadingDialog != null) loadingDialog.dismiss();
    }

    @Override
    public void onGetListAttachment(ArrayList<Attachment> listAttachment) {
        this.listAttachment = listAttachment;
        if (listAttachment == null || listAttachment.isEmpty()) {
            btnOpenFile.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetFileUrl(String fileUrl) {
        if (loadingDialog != null) loadingDialog.dismiss();

        Intent i = new Intent(DetailMessageActivity.this, WebviewActivity.class);
        i.putExtra(AppConfig.FILE_URL, fileUrl);
        startActivity(i);
    }

    @Override
    public void onGetReceiver(Receiver receiver) {
        tvSender.setText("Người nhận: " + receiver.getEmployeeName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_open_file:
                ListAttachmentDialog dialog = new ListAttachmentDialog(DetailMessageActivity.this, listAttachment, new ListAttachmentDialog.IOnOpenFile() {
                    @Override
                    public void onClickFile(Attachment attachment) {
                        loadingDialog = new LoadingDialog(DetailMessageActivity.this);
                        loadingDialog.show();

                        String fileType = AppConfig.TYPE_MESSAGE;
                        presenter.getFileUrl(fileType, attachment.getMessageAttachmentId());

                        /*currentFileType = Utils.getDocumentMimeType(attachment.getFileName());
                        Uri fileUri = messageType == 0 ? Uri.parse(AppConfig.DOWNLOAD_IN_URL + attachment.getId()) : Uri.parse(AppConfig.DOWNLOAD_OUT_URL + attachment.getId());
                        Utils.startDownload(fileUri, attachment.getFileName(), downloadManager, DetailMessageActivity.this);*/
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }
}
