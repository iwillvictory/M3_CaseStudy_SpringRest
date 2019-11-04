package com.codegym.models;

import java.util.Date;


public class EmployeeForm {

    private int employeeId;
    private String employeeName;
    private String mail;
    private Date joinDate;

    private int department;

    public EmployeeForm(String employeeName, String mail, Date joinDate,int department) {
        this.employeeName = employeeName;
        this.mail = mail;
        this.joinDate = joinDate;
        this.department = department;
    }

    public EmployeeForm() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", mail='" + mail + '\'' +
                ", joinDate=" + joinDate +
                ", department=" + department +
                '}';
    }
}
