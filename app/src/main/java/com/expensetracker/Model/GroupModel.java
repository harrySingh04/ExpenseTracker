package com.expensetracker.Model;

/**
 * Created by user on 09-07-2017.
 */

public class GroupModel {


    String name;
    int user_id;
    int group_id;

    public GroupModel(String name, int user_id, int group_id) {
        super();
        this.name = name;
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }


}
