package com.expensetracker.Model;

import com.expensetracker.Model.UserModel;

import java.util.ArrayList;

public class GroupModel {

    String name;
    Integer group_id;
    ArrayList<UserModel> userDetails;



    public GroupModel(String name, Integer group_id, ArrayList<UserModel> userDetails) {
        super();
        this.name = name;
        this.group_id = group_id;
        this.userDetails = userDetails;
    }


    public GroupModel(int group_id, String name) {
        super();
        this.name = name;
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public ArrayList<UserModel> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(ArrayList<UserModel> userDetails) {
        this.userDetails = userDetails;
    }


}
