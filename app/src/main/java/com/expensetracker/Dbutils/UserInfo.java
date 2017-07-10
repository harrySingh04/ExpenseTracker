package com.expensetracker.Dbutils;

import com.expensetracker.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by user on 08-07-2017.
 */

public class UserInfo {


    AsyncResponse asyncResponse;

    public UserInfo(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public void get_users(String username, String password) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/getuser";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }

    public void insert_user(String username, String password, String email) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/registeruser";

        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void delete_user(int id) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/deleteuser";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("id", id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void edit_user(int id, String password, String email) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/registeruser";

        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("id", id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }





}
