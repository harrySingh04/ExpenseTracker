package com.expensetracker.Model;

/**
 * Created by user on 03-07-2017.
 */

public class Category {

    int id;
    String name;
    int expenseID;

    public Category(int id, String name, int expenseID) {
        this.id = id;
        this.name = name;
        this.expenseID = expenseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }




}
