package com.alink.documentmanagement.calendar;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alink.documentmanagement.models.Calendar;
import com.alink.documentmanagement.R;
import com.alink.documentmanagement.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCalendarAdapter extends RecyclerView.Adapter<ListCalendarAdapter.CalendarViewHolder> {
    public Context context;
    public ArrayList<Calendar> listCalendar;
    public IListAction listener;

    public ListCalendarAdapter(Context context, ArrayList<Calendar> listCalendar, IListAction listener) {
        this.context = context;
        this.listCalendar = listCalendar;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_calendar, parent, false);
        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, final int position) {
        if (listCalendar.size() > 0) {
            holder.bindData(listCalendar.get(position));
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
        return listCalendar.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvTime, tvTitle, tvContent, tvAttach;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvAttach = itemView.findViewById(R.id.tv_attach);
        }

        public void bindData(Calendar calendar) {
            if (calendar == null) return;

            tvDate.setText(Utils.formatToDate(calendar.getStartTime()));
            tvTime.setText("Từ " + Utils.formatToTime(calendar.getStartTime()) + " đến " + Utils.formatToTime(calendar.getEndTime()));
            tvTitle.setText(calendar.getTitle());

            if (calendar.getContent() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvContent.setText(Html.fromHtml(calendar.getContent(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvContent.setText(Html.fromHtml(calendar.getContent()));
                }
            }

            if (calendar.getAttachments().size() > 0) {
                tvAttach.setVisibility(View.VISIBLE);
                tvAttach.setText(calendar.getAttachments().size() + " file đính kèm");
            } else {
                tvAttach.setVisibility(View.GONE);
            }
        }
    }

    public interface IListAction {
        void onItemClicked(int position);
    }
}
