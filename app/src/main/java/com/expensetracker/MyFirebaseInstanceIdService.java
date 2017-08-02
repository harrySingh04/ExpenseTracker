package com.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import com.expensetracker.Dbutils.UserInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by user on 04-07-2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";
    SharedPreferences sharedPreferences;
    UserInfo userinfo;


    @Override
    public void onTokenRefresh() {

            String recent_token = FirebaseInstanceId.getInstance().getToken();

        Log.e(REG_TOKEN,recent_token);

        userinfo = new UserInfo();
        sharedPreferences = getApplicationContext().getSharedPreferences("data",MODE_PRIVATE);
        int id = sharedPreferences.getInt("userid",0);
        userinfo.reg_new_token(id,REG_TOKEN, new AsyncResponse() {
            @Override
            public void sendData(String data) {

            }
        });


    }
}
