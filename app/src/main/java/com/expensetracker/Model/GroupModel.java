package com.expensetracker.Model;

import java.util.ArrayList;

/**
 * Created by user on 09-07-2017.
 */

public class GroupModel {


    String name;
    Integer user_id;
    Integer group_id;
    ArrayList<UserModel> group_members;

    public GroupModel(String name, int user_id, int group_id) {
        super();
        this.name = name;
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public GroupModel(String name, int group_id, int user_id, ArrayList<UserModel> group_members) {
        this.name = name;
        this.group_id = group_id;
        this.user_id = user_id;
        this.group_members = group_members;
    }

    public GroupModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }


    public String toString(){

        return name;
    }


}
