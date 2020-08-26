package com.alink.documentmanagement.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListAttachmentDialog extends Dialog {
    public Context context;
    public RecyclerView rcvListAttachment;
    public ListAttachmentAdapter adapter;
    public ArrayList<Attachment> listAttachment;
    public IOnOpenFile listener;

    public ListAttachmentDialog(@NonNull Context context, ArrayList<Attachment> listAttachment, IOnOpenFile listener) {
        super(context);
        this.context = context;
        this.listAttachment = listAttachment;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setContentView(R.layout.dialog_list_attachment);

        rcvListAttachment = findViewById(R.id.rcv_list_attachment);

        adapter = new ListAttachmentAdapter(context, listAttachment, new ListAttachmentAdapter.IListAction() {
            @Override
            public void onItemClicked(int position) {
                listener.onClickFile(listAttachment.get(position));
            }
        });
        rcvListAttachment.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rcvListAttachment.setAdapter(adapter);
    }

    public interface IOnOpenFile {
        void onClickFile(Attachment attachment);
    }
}
