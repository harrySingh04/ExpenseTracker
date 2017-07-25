package com.expensetracker.Dbutils;

import android.content.SharedPreferences;
import android.util.Log;

import com.expensetracker.Constants;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.AsyncData;
import com.expensetracker.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by user on 17-07-2017.
 */

public class FriendsInfo {



    private static String TAG = "FriendsInfo";

    public FriendsInfo() {

    }

    public void getallfriends(int userid, AsyncResponse asyncResponse) {

        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.GET_FRIEND;
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();
            jsonObject.put("id", userid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }


    public void addfriend(int userid, String email,String username, AsyncResponse asyncResponse) {


        Log.e(TAG, String.valueOf(userid));
        Log.e(TAG, String.valueOf(email));


        NetworkUtils networkUtils = new NetworkUtils(asyncResponse);
        String stringurl = Constants.ADD_FRIEND;
        URL url = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(stringurl);
            jsonObject = new JSONObject();

            jsonObject.put("userid", userid);
            jsonObject.put("SingleEmail", email);
            jsonObject.put("username", email);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncData asyncTaskdata = new AsyncData(url, jsonObject);
        networkUtils.execute(asyncTaskdata);

    }

    public void deletefriend(){









    }


}
