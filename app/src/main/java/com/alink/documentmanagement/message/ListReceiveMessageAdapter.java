package com.alink.documentmanagement.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.models.ReceiveMessage;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListReceiveMessageAdapter extends RecyclerView.Adapter<ListReceiveMessageAdapter.MessageViewHolder> {
    public Context context;
    public ArrayList<ReceiveMessage> listMessage;
    public IListAction listener;

    public ListReceiveMessageAdapter(Context context, ArrayList<ReceiveMessage> listMessage, IListAction listener) {
        this.context = context;
        this.listMessage = listMessage;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, final int position) {
        if (listMessage.size() > 0) {
            holder.bindData(listMessage.get(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSender, tvContent, tvTime, tvAttach;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSender = itemView.findViewById(R.id.tv_sender);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAttach = itemView.findViewById(R.id.tv_attach);
        }

        public void bindData(ReceiveMessage message) {
            if (message == null) return;

            tvSender.setText(message.getSendByName());
            tvContent.setText(message.getTitle());
            tvTime.setText(Utils.formatToDateTime(message.getCreatedDate()));
            if (message.getAttachmentsCount() > 0) {
                tvAttach.setVisibility(View.VISIBLE);
                tvAttach.setText(message.getAttachmentsCount() + " file đính kèm");
            } else {
                tvAttach.setVisibility(View.GONE);
            }
        }
    }

    public interface IListAction {
        void onItemClicked(int position);
    }
}
