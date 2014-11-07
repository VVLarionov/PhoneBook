package com.getjavajobs.larionov.phonebook.model;

import java.util.List;

/**
 * Created by Vlad on 07.08.2014.
 */
public class Employee {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String project;
    private Department department;
    private List<Phone> phoneList;
    private String email;

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getProject() {
        return project;
    }
    public Department getDepartment() {
        return department;
    }
    public List<Phone> getPhoneList() {
        return phoneList;
    }
    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setProject(String projectName) {
        this.project = projectName;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
