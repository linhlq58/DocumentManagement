package com.alink.documentmanagement.documentReceived;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.models.DocumentIn;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListDocumentReceivedAdapter extends RecyclerView.Adapter<ListDocumentReceivedAdapter.DocumentViewHolder> {
    public Context context;
    public ArrayList<DocumentIn> listDocument;
    public IListAction iListAction;

    public ListDocumentReceivedAdapter(Context context, ArrayList<DocumentIn> listDocument, IListAction iListAction) {
        this.context = context;
        this.listDocument = listDocument;
        this.iListAction = iListAction;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_document, parent, false);
        return new DocumentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, final int position) {
        if (listDocument.size() > 0) {
            holder.bindData(listDocument.get(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListAction.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDocument.size();
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvTime, tvSender, tvOpinion, tvAttach;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvSender = itemView.findViewById(R.id.tv_sender);
            tvOpinion = itemView.findViewById(R.id.tv_opinion);
            tvAttach = itemView.findViewById(R.id.tv_attach);
        }

        public void bindData(DocumentIn document) {
            if (document == null) return;

            tvTitle.setText(document.getTitle());
            tvTime.setText(Utils.formatToDateTime(document.getCreatedDate()));
            tvSender.setText("Người gửi: " + document.getCreatedBy());
            tvOpinion.setText(document.getCommentsCount() + " ý kiến");
            if (document.getAttachmentsCount() > 0) {
                tvAttach.setVisibility(View.VISIBLE);
                tvAttach.setText(document.getAttachmentsCount() + " file đính kèm");
            } else {
                tvAttach.setVisibility(View.GONE);
            }
        }
    }

    public interface IListAction {
        void onItemClicked(int position);
    }
}
