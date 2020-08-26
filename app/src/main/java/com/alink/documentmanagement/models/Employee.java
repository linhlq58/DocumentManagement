package com.alink.documentmanagement.models;

import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("Id")
    private String id;

    @SerializedName("EmployeeName")
    private String employeeName;

    @SerializedName("EmployeeId")
    private String employeeId;

    @SerializedName("DepartmentName")
    private String departmentName;

    private boolean isChecked = false;

    private int action = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
