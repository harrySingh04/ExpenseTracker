package com.expensetracker.Model;

/**
 * Created by user on 03-07-2017.
 */

public class Expense {



    int id;
    int amount;
    String date;

    public Expense(int id, int amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }







}



