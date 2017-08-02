package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.expensetracker.Dbutils.UserInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.R;

import org.json.JSONObject;

public class LoginUser extends AppCompatActivity {

    private EditText username, password;
    private Button login, register;
    AsyncResponse asyncResponse;
    UserInfo userInfo;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String TAG = "LoginUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        final EditText password = (EditText) findViewById(R.id.password);
        userInfo = new UserInfo();
        context = this;
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty()) {
                    String message = "Username cannot be empty";
                    showAlertDialog(message);
                } else if (password.getText().toString().isEmpty()) {
                    String message = "Password cannot be empty";
                    showAlertDialog(message);
                } else {

                    userInfo.get_users(username.getText().toString(), password.getText().toString(), new AsyncResponse() {
                        @Override
                        public void sendData(String data) {
                            try {
                                Log.e(TAG, "data comming from apllicaton" + data);
                                if (!data.equals("null")) {

                                    JSONObject main = new JSONObject(data);
                                    String name = main.getString("username");
                                    int id = main.getInt("id");
                                    String email = main.getString("email");
                                    sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE); //1
                                    editor = sharedPreferences.edit();

                                    editor.putString("username", name);
                                    editor.putString("email", email);
                                    editor.putInt("userid", id);
                                    editor.commit();

                                    Intent intent = new Intent();
                                    intent.setClass(context, Home.class);
                                    startActivity(intent);
                                } else {
                                    String message = "You seem to have entered the wrong credentials. Please enter the correct credentials";
                                    showAlertDialog(message);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "error", e);
                            }
                        }
                    });
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, RegisterUser.class);
                startActivity(intent);

            }
        });


    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }


        });
        builder1.create().show();


    }
}