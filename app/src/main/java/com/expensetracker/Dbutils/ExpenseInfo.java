package com.expensetracker.Dbutils;

import android.util.Log;

import com.expensetracker.Constants;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by user on 09-07-2017.
 */

public class ExpenseInfo {


    public static String TAG = "ExpenseInfo";

    public ExpenseInfo() {

    }

    public void getsingleexpense(int id, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.GET_SINGLE_EXPENSE;
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

    public void getallexpense(int userid, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);

        String stringurl = Constants.GET_ALL_EXPENSE;

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


    public void editexpense(int id, int amount, String date, int user_id, String description, String category, Integer groupid, AsyncResponse asyncResponse) {



        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.EDIT_EXPENSE;
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
            Log.e(TAG,"error"+e);
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


    public void deleteexpense(int id, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.DELETE_EXPENSE;
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

    public void addexpense(int amount, String date, int user_id, String description, String category, int groupid, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.ADD_EXPENSE;
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

    public void addexpense(int amount, String date, int user_id, String description, String category, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.ADD_EXPENSE;
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

            Log.e("amount",String.valueOf(amount));
            Log.e("amount",String.valueOf(date));
            Log.e("amount",String.valueOf(user_id));
            Log.e("amount",String.valueOf(description));
            Log.e("amount",String.valueOf(category));


        } catch (Exception e) {
            Log.e(TAG,"erroe",e);
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);
    }


}
