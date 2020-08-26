package com.alink.documentmanagement.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alink.documentmanagement.models.ServerDto;
import com.alink.documentmanagement.R;

import java.util.ArrayList;

public class ListServerAdapter extends RecyclerView.Adapter<ListServerAdapter.ServerViewHolder> {
    public Context context;
    public ArrayList<ServerDto> listServer;

    public ListServerAdapter(Context context, ArrayList<ServerDto> listServer) {
        this.context = context;
        this.listServer = listServer;
    }

    @NonNull
    @Override
    public ServerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_server, parent, false);
        return new ServerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerViewHolder holder, int position) {
        holder.bindData(listServer.get(position));
    }

    @Override
    public int getItemCount() {
        return listServer.size();
    }

    public class ServerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServerName;

        public ServerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvServerName = itemView.findViewById(R.id.tv_server);
        }

        public void bindData(ServerDto serverDto) {
            if (serverDto == null) return;

            tvServerName.setText(serverDto.getAppName());
        }
    }
}
