package com.alink.documentmanagement.detail.documentDetail;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alink.documentmanagement.appConfig.AppConfig;
import com.alink.documentmanagement.createNewMessage.ListReceiverHorizontalAdapter;
import com.alink.documentmanagement.detail.ListCommentAdapter;
import com.alink.documentmanagement.listReceiver.ListReceiverActivity;
import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.models.Comment;
import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.models.DocumentOut;
import com.alink.documentmanagement.models.Recipent;
import com.alink.documentmanagement.MyApplication;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.dialog.ListAttachmentDialog;
import com.alink.documentmanagement.dialog.LoadingDialog;
import com.alink.documentmanagement.main.WebviewActivity;
import com.alink.documentmanagement.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, DetailInterface.IDetailView {
    public DownloadManager downloadManager;
    public RecyclerView recyclerView;
    public ListCommentAdapter adapter;
    public ArrayList<Comment> listComment;

    public RecyclerView rcvReceiver;
    public ListReceiverHorizontalAdapter receiverAdapter;
    public ArrayList<String> listReceiver;
    public ArrayList<Recipent> listRecipent;

    public RelativeLayout btnBack, btnReply, btnFinish, rtlBot;
    public TextView tvTitle, tvSender, tvTime, tvContent, tvCmtCount, tvChooseReceiver, tvReply;
    public TextView btnOpenFile;
    public EditText editComment;
    public View shadowBot;

    public LoadingDialog loadingDialog;
    public ArrayList<Attachment> listAttachment = new ArrayList<>();
    public BroadcastReceiver onComplete;

    public static final int REQUEST_RECEIVER = 6969;
    public String documentId;
    public String userId;
    public int documentType;
    public boolean isCompleted;
    public String currentFileType;

    public DetailPresenter presenter = new DetailPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnBack = findViewById(R.id.btn_back);
        btnReply = findViewById(R.id.btn_reply);
        btnFinish = findViewById(R.id.btn_finish);
        rtlBot = findViewById(R.id.rtl_bot);
        recyclerView = findViewById(R.id.recyclerView);
        tvTitle = findViewById(R.id.tv_title);
        tvSender = findViewById(R.id.tv_sender);
        tvTime = findViewById(R.id.tv_time);
        tvContent = findViewById(R.id.tv_content);
        tvCmtCount = findViewById(R.id.tv_comment_count);
        tvChooseReceiver = findViewById(R.id.tv_choose_receiver);
        tvReply = findViewById(R.id.tv_reply);
        btnOpenFile = findViewById(R.id.btn_open_file);
        editComment = findViewById(R.id.edit_comment);
        shadowBot = findViewById(R.id.shadow_bot);
        rcvReceiver = findViewById(R.id.rcv_receiver);

        initData();
    }

    public void initData() {
        presenter.checkPermission(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));

        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        listReceiver = new ArrayList<>();
        listRecipent = new ArrayList<>();

        receiverAdapter = new ListReceiverHorizontalAdapter(this, listReceiver);
        rcvReceiver.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvReceiver.setAdapter(receiverAdapter);

        documentId = getIntent().getStringExtra(AppConfig.DOCUMENT_ID);
        documentType = getIntent().getIntExtra(AppConfig.DOCUMENT_TYPE, 0);
        userId = getIntent().getStringExtra(AppConfig.DOCUMENT_USER_ID);
        isCompleted = getIntent().getBooleanExtra(AppConfig.IS_COMPLETED, false);

        if (isCompleted) {
            tvChooseReceiver.setVisibility(View.GONE);
            rtlBot.setVisibility(View.GONE);
            shadowBot.setVisibility(View.GONE);
            btnFinish.setVisibility(View.GONE);
        }

        if (documentType == 0) {
            tvReply.setText("Chuyển VB");
            btnFinish.setVisibility(View.GONE);
            presenter.getListIn(documentId);
            presenter.getListCommentIn(documentId);
        } else {
            tvReply.setText("Chuyển VB");
            presenter.getListOut(documentId);
            presenter.getListCommentOut(documentId);
        }

        listComment = new ArrayList<>();

        adapter = new ListCommentAdapter(this, listComment, documentType);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(this);
        tvChooseReceiver.setOnClickListener(this);
        btnReply.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnOpenFile.setOnClickListener(this);

        onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadingDialog.dismiss();

                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(DetailActivity.this, "Completed with id: " + referenceId, Toast.LENGTH_SHORT).show();

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(referenceId);

                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        Utils.onOpenFile(Uri.parse("content://" + Uri.parse(uriString).getPath()), currentFileType, DetailActivity.this);
                    }
                }
            }
        };

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_choose_receiver:
                Intent i = new Intent(this, ListReceiverActivity.class);
                i.putExtra(AppConfig.IS_FROM_DOCUMENT, true);
                if (documentType == 0) {
                    i.putExtra(AppConfig.IS_DOCUMENT_IN, true);
                }
                startActivityForResult(i, REQUEST_RECEIVER);
                break;
            case R.id.btn_reply:
                if (documentType == 0) {
                    if (listRecipent.size() > 0) {
                        presenter.transferDocumentIn(documentId, userId, editComment.getText().toString(), listRecipent);
                    } else {
                        Toast.makeText(this, "Danh sách gửi trống!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (listReceiver.size() > 0) {
                        presenter.sendDocument(documentId, false, editComment.getText().toString(), listReceiver);
                    } else {
                        Toast.makeText(this, "Danh sách gửi trống!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_finish:
                presenter.chuyenPhatHanh(documentId, "Chuyển phát hành");
                break;
            case R.id.btn_open_file:
                ListAttachmentDialog dialog = new ListAttachmentDialog(DetailActivity.this, listAttachment, new ListAttachmentDialog.IOnOpenFile() {
                    @Override
                    public void onClickFile(Attachment attachment) {
                        loadingDialog = new LoadingDialog(DetailActivity.this);
                        loadingDialog.show();

                        String fileType = documentType == 0 ? AppConfig.TYPE_DOCUMENT_IN : AppConfig.TYPE_DOCUMENT_OUT;
                        presenter.getFileUrl(fileType, attachment.getId());

                        /*currentFileType = Utils.getDocumentMimeType(attachment.getFileName());
                        Uri fileUri = documentType == 0 ? Uri.parse(AppConfig.DOWNLOAD_IN_URL + attachment.getId()) : Uri.parse(AppConfig.DOWNLOAD_OUT_URL + attachment.getId());
                        startDownload(fileUri, attachment.getFileName());*/
                    }
                });
                dialog.show();

                break;
        }
    }

    public long startDownload(Uri uri, String fileName) {
        long downloadReference;

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Document Download");
        request.setDescription("Document Download");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName);

        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RECEIVER) {
            if (resultCode == RESULT_OK) {
                if (documentType == 0) {
                    ArrayList<Recipent> listRecipentGet = (ArrayList<Recipent>) data.getSerializableExtra(AppConfig.LIST_RECEIVER);
                    this.listRecipent.addAll(listRecipentGet);

                    ArrayList<String> listString = new ArrayList<>();
                    if (listRecipentGet.size() > 0) {
                        for (int i = 0; i < listRecipentGet.size(); i++) {
                            String name = listRecipentGet.get(i).getUserName();
                            listString.add(name);
                        }
                    }

                    listReceiver.clear();
                    listReceiver.addAll(listString);
                    receiverAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<String> listString = data.getStringArrayListExtra(AppConfig.LIST_RECEIVER);
                    listReceiver.clear();
                    listReceiver.addAll(listString);
                    receiverAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onUpdateDetailIn(DocumentIn documentIn) {
        if (documentIn != null) {
            tvTitle.setText(documentIn.getTitle());
            tvSender.setText("Người gửi: " + documentIn.getCreatedBy());
            tvTime.setText("Thời gian: " + Utils.formatToDateTime(documentIn.getReceivedDate()));
            tvContent.setText("");
            //userId = documentIn.getUserId();
        }
    }

    @Override
    public void onUpdateDetailOut(DocumentOut documentOut) {
        if (documentOut != null) {
            tvTitle.setText(documentOut.getTitle());
            tvSender.setText("Người gửi: " + documentOut.getCreatedBy());
            tvTime.setText("Thời gian: " + Utils.formatToDateTime(documentOut.getCreatedDate()));
            tvContent.setText("");
        }
    }

    @Override
    public void onUpdateComment(List<Comment> listComment) {
        this.listComment.addAll(listComment);
        adapter.notifyDataSetChanged();

        tvCmtCount.setText(listComment.size() + " ý kiến");
    }

    @Override
    public void onSendSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();

        Comment comment = new Comment();
        comment.setEmployeeName(MyApplication.getSharedPreferences().getString(AppConfig.USER_NAME, ""));
        comment.setComment(editComment.getText().toString());
        comment.setModifiedDate(fromUser.format(date));

        listComment.add(comment);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(listComment.size() - 1);

        editComment.setText("");
    }

    @Override
    public void onSendDocumentSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSendFailed(String msg) {
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
    public void displayPhatHanh(boolean hasPermission) {
        if (hasPermission && !isCompleted) {
            if (documentType != 0) {
                btnFinish.setVisibility(View.VISIBLE);
            }
        } else {
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetFileUrl(String fileUrl) {
        if (loadingDialog != null) loadingDialog.dismiss();

        Intent i = new Intent(DetailActivity.this, WebviewActivity.class);
        i.putExtra(AppConfig.FILE_URL, fileUrl);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }
}
