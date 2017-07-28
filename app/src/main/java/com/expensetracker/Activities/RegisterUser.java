package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.expensetracker.Dbutils.UserInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.R;

public class RegisterUser extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private Button register;
    UserInfo userInfo;
    AsyncResponse asyncResponse;
    Context context;
    public static String TAG = "register user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        context = this;
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.useremail);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInfo.insert_user(username.getText().toString(), password.getText().toString(), email.getText().toString(),
                        asyncResponse = new AsyncResponse() {
                            @Override
                            public void sendData(String data) {

                                if (Integer.parseInt(data) == 0) {
                                    Intent intent = new Intent();
                                    intent.setClass(context, LoginUser.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setMessage("Either username or email already exists with us. Please choose a new username or email");
                                    alert.setCancelable(true);
                                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }


                                    });
                                    alert.create().show();

                                }
                            }


                        });

            }
        });


        userInfo = new UserInfo();

    }
}