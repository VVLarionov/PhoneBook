package com.getjavajobs.larionov.phonebook.model;

/**
 * Created by Vlad on 07.08.2014.
 */
public class Department{

    private int id;
    private String name;
    private Employee manager;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Employee getManager() {
        return manager;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}