package com.alink.documentmanagement.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.models.Comment;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.CommentViewHolder> {
    public Context context;
    public ArrayList<Comment> listComment;
    public int documentType;

    public ListCommentAdapter(Context context, ArrayList<Comment> listComment, int documentType) {
        this.context = context;
        this.listComment = listComment;
        this.documentType = documentType;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView, documentType);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (listComment.size() > 0) {
            holder.bindData(listComment.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public int documentType;
        public TextView tvSender, tvTime, tvContent;

        public CommentViewHolder(@NonNull View itemView, int documentType) {
            super(itemView);
            this.documentType = documentType;

            tvSender = itemView.findViewById(R.id.tv_sender);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bindData(Comment comment) {
            if (comment == null) return;

            tvSender.setText(comment.getEmployeeName());
            tvTime.setText(Utils.formatToDateTime(comment.getModifiedDate()));
            if (documentType == 0) {
                tvContent.setText(comment.getBody());
            } else {
                tvContent.setText(comment.getComment());
            }
        }
    }
}
