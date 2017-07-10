package com.expensetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.expensetracker.Dbutils.UserInfo;

import org.json.JSONObject;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    JSONObject main = new JSONObject(data);

                    String name = main.getString("id");
                    String id = main.getString("username");
                    String email = main.getString("email");
                    Log.e("name", "I am here");
                    Log.e("name", name);
                    Log.e("id", id);
                    Log.e("email", email);

                }catch (Exception e){
                    Log.e("error","error",e);

                }









              Log.e("this is trhe dta",data);
            }
        };

        UserInfo userInfo = new UserInfo(asyncResponse);

        userInfo.get_users("vivek","vivek");


    }
}
