package com.expensetracker.Dbutils;

import android.util.Log;

import com.expensetracker.Constants;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 09-07-2017.
 */

public class GroupInfo {



    public static String TAG = "groupinfo";

    public GroupInfo(){
    }

    public void addgroup(ArrayList<String> email, String groupname,AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.ADD_GROUP;
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

    public void addGroupFromId(ArrayList<Integer> ids, String groupname,AsyncResponse asyncResponse) {


        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.ADD_GROUP_FROM_ID;
        URL url = null;
        JSONObject jsonObject = null;
        JSONArray arr = new JSONArray();

        for(Integer i:ids){
            arr.put(i);
        }


        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("userids", arr);
            jsonObject.put("groupname", groupname);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e(TAG,String.valueOf(jsonObject));
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }

    public void deletegroup(int group_id,AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = Constants.DELETE_GROUP;

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


    public void getgroupmembers(int group_id,AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.GET_GROUP_MEMBERS;
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

    public void getAllGroupsForUser(int user_id,AsyncResponse asyncResponse) {


        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.GET_GROUP_MEMBERS_FOR_SINGLE_USER;
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

    public void getGroupExpense(int groupID,AsyncResponse asyncResponse){

        Log.e(TAG,String.valueOf(groupID));
        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.GET_GROUP_EXPENSE;
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("group_id", groupID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


}
