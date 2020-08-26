package com.alink.documentmanagement.listReceiver;

import com.alink.documentmanagement.models.Employee;

import java.util.List;

public class ListReceiverInterface {
    interface IReceiverView {
        void onUpdateList(List<Employee> listEmployee);
    }

    interface IReceiverFunction {
        void getListEmployee(boolean isLocalOnly);

        void getListEmployeeDocumentIns(String userName);

        void getListEmployeeDocumentOuts(String userName);
    }
}
