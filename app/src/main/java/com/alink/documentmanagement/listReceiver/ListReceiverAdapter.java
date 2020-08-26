package com.alink.documentmanagement.listReceiver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alink.documentmanagement.models.Employee;
import com.alink.documentmanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListReceiverAdapter extends RecyclerView.Adapter<ListReceiverAdapter.ListReceiverViewHolder> implements Filterable {
    public Context context;
    public ArrayList<Employee> listEmployee;
    public ArrayList<Employee> listEmployeeOriginal;
    public boolean isDocumentIn;

    public ListReceiverAdapter(Context context, ArrayList<Employee> listEmployee, boolean isDocumentIn) {
        this.context = context;
        this.listEmployee = listEmployee;
        this.listEmployeeOriginal = listEmployee;
        this.isDocumentIn = isDocumentIn;
    }

    @NonNull
    @Override
    public ListReceiverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_employee, parent, false);
        return new ListReceiverViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListReceiverViewHolder holder, int position) {
        if (listEmployee.size() > 0) {
            holder.bindData(listEmployee.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return listEmployee == null ? 0 : listEmployee.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listEmployee = (ArrayList<Employee>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Employee> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = listEmployeeOriginal;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected ArrayList<Employee> getFilteredResults(String constraint) {
        ArrayList<Employee> results = new ArrayList<>();

        for (Employee item : listEmployeeOriginal) {
            if (item.getEmployeeName() != null && item.getEmployeeId() != null) {
                if (item.getEmployeeName().toLowerCase().contains(constraint) || item.getEmployeeId().toLowerCase().contains(constraint)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    public class ListReceiverViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvId, tvDepartment;
        private LinearLayout layoutBottom;
        private CheckBox checkBox, checkBoxXuly, checkBoxPhoihop, checkBoxDebiet;

        public ListReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            tvDepartment = itemView.findViewById(R.id.tv_department);
            layoutBottom = itemView.findViewById(R.id.layout_bottom);
            checkBox = itemView.findViewById(R.id.checkbox);
            checkBoxXuly = itemView.findViewById(R.id.checkbox_xuly);
            checkBoxPhoihop = itemView.findViewById(R.id.checkbox_phoihop);
            checkBoxDebiet = itemView.findViewById(R.id.checkbox_debiet);

            if (isDocumentIn) {
                checkBox.setVisibility(View.GONE);
                layoutBottom.setVisibility(View.VISIBLE);

                checkBoxXuly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            checkBoxPhoihop.setChecked(false);
                            checkBoxDebiet.setChecked(false);
                            listEmployee.get(getAdapterPosition()).setAction(1);
                        } else {
                            listEmployee.get(getAdapterPosition()).setAction(0);
                        }
                    }
                });

                checkBoxPhoihop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            checkBoxXuly.setChecked(false);
                            checkBoxDebiet.setChecked(false);
                            listEmployee.get(getAdapterPosition()).setAction(2);
                        } else {
                            listEmployee.get(getAdapterPosition()).setAction(0);
                        }
                    }
                });

                checkBoxDebiet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            checkBoxXuly.setChecked(false);
                            checkBoxPhoihop.setChecked(false);
                            listEmployee.get(getAdapterPosition()).setAction(3);
                        } else {
                            listEmployee.get(getAdapterPosition()).setAction(0);
                        }
                    }
                });
            } else {
                checkBox.setVisibility(View.VISIBLE);
                layoutBottom.setVisibility(View.GONE);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            listEmployee.get(getAdapterPosition()).setChecked(true);
                        } else {
                            listEmployee.get(getAdapterPosition()).setChecked(false);
                        }
                    }
                });
            }

            if (!isDocumentIn) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkBox.performClick();
                    }
                });
            }
        }

        public void bindData(Employee employee) {
            if (employee == null) return;

            tvName.setText(employee.getEmployeeName());
            tvId.setText(employee.getEmployeeId());
            tvDepartment.setText(employee.getDepartmentName());
        }
    }
}
