package com.alink.documentmanagement.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.models.Attachment;
import com.alink.documentmanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAttachmentAdapter extends RecyclerView.Adapter<ListAttachmentAdapter.ListAttachmentViewHolder> {
    public Context context;
    public ArrayList<Attachment> listAttachment;
    public IListAction iListAction;

    public ListAttachmentAdapter(Context context, ArrayList<Attachment> listAttachment, IListAction iListAction) {
        this.context = context;
        this.listAttachment = listAttachment;
        this.iListAction = iListAction;
    }

    @NonNull
    @Override
    public ListAttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_attachment, parent, false);
        return new ListAttachmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAttachmentViewHolder holder, final int position) {
        if (listAttachment.size() > 0) {
            holder.bindData(listAttachment.get(position));
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
        return listAttachment.size();
    }

    public class ListAttachmentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ListAttachmentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }

        public void bindData(Attachment attachment) {
            tvName.setText(attachment.getFileName());
        }
    }

    public interface IListAction {
        void onItemClicked(int position);
    }
}
