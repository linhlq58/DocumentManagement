package com.alink.documentmanagement.createNewMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListReceiverHorizontalAdapter extends RecyclerView.Adapter<ListReceiverHorizontalAdapter.ListReceiverHorizontalViewHolder> {
    public Context context;
    public ArrayList<String> listEmployeeName;

    public ListReceiverHorizontalAdapter(Context context, ArrayList<String> listEmployeeName) {
        this.context = context;
        this.listEmployeeName = listEmployeeName;
    }

    @NonNull
    @Override
    public ListReceiverHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_receiver, parent, false);
        return new ListReceiverHorizontalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListReceiverHorizontalViewHolder holder, int position) {
        if (listEmployeeName.size() > 0) {
            holder.bindData(listEmployeeName.get(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listEmployeeName.size();
    }

    public class ListReceiverHorizontalViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public ListReceiverHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }

        public void bindData(String name) {
            if (name == null) return;

            tvName.setText(name);
        }
    }
}
