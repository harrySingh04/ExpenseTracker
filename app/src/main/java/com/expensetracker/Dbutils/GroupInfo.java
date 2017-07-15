package com.expensetracker.Dbutils;

import com.expensetracker.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 09-07-2017.
 */

public class GroupInfo {


    AsyncResponse asyncResponse;

    public GroupInfo(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public void addgroup(ArrayList<String> email, String groupname) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/addgroup";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("groupname", groupname);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }

    public void deletegroup(int group_id) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/deletegroup";

        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("group_id", group_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void editgroup(int id, int amount, Date date, int user_id, String description, String category, int groupid) {


    }


    public void getgroupmembers(int group_id) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/getgroupmembers";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("group_id", group_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }

    public void getAllGroupsForUser(int user_id){


        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = "http://cs3.calstatela.edu:8080/cs3220stu52/getAllGroupsForUser";
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("user_id", user_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }


}
