package com.expensetracker.Dbutils;

import com.expensetracker.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.Date;

/**
 * Created by user on 09-07-2017.
 */

public class ExpenseInfo {

    AsyncResponse asyncResponse;

    public ExpenseInfo(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public void getsingleexpense(int id) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/getsingleexpense";
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

    public void getallexpense(int userid) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/getallexpense";

        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("userID", userid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void editexpense(int id, int amount, Date date, int user_id, String description, String category, int groupid) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/editexpense";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("amount", amount);
            jsonObject.put("date", date);
            jsonObject.put("userID", user_id);
            jsonObject.put("description", description);
            jsonObject.put("category", category);
            jsonObject.put("groupID", groupid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void deleteexpense(int id) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/deleteexpense";
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

    public void addexpense(int amount, Date date, int user_id, String description, String category, int groupid) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/addexpense";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("amount", amount);
            jsonObject.put("date", date);
            jsonObject.put("userID", user_id);
            jsonObject.put("description", description);
            jsonObject.put("category", category);
            jsonObject.put("groupID", groupid);


        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


}
