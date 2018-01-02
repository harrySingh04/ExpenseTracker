package com.expensetracker.Model;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseModel {

    private Integer id;
    private Float amount;
    private String date;
    private String description;
    private String category;
    private UserModel userDetails;
    private GroupModel groupDetails;

//	public ExpenseModel(Integer id, Float amount, String date, String description, String category) {
//		super();
//		this.id = id;
//		this.amount = amount;
//		this.date = date;
//		this.description = description;
//		this.category = category;
//	}
//
//	public ExpenseModel(Float amount, String date, String decription, String category) {
//		super();
//		this.amount = amount;
//		this.date = date;
//		this.description = decription;
//		this.category = category;
//	}
//
//	public ExpenseModel(int id, Float amount, String date, String decription, String category) {
//		super();
//		this.id = id;
//		this.amount = amount;
//		this.date = date;
//		this.description = decription;
//		this.category = category;
//	}

    public ExpenseModel(Integer id, Float amount, String date, String description, String category,
                        UserModel userDetails) {
        super();
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.userDetails = userDetails;
    }

    public ExpenseModel(Integer id, Float amount, String date, String description, String category,
                          UserModel userDetails, GroupModel groupDetails) {
        super();
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.userDetails = userDetails;
        this.groupDetails = groupDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserModel getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserModel userDetails) {
        this.userDetails = userDetails;
    }

    public GroupModel getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(GroupModel groupDetails) {
        this.groupDetails = groupDetails;
    }

}
