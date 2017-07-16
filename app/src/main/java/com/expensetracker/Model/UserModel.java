package com.expensetracker.Model;

/**
 * Created by user on 15-07-2017.
 */

public class UserModel {

    int user_id;
    String username;
    String email;

    public UserModel(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
    }

    public UserModel(int user_id, String username, String email) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
    }

    public UserModel(String username, int user_id, String email) {
        this.username = username;
        this.user_id = user_id;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
