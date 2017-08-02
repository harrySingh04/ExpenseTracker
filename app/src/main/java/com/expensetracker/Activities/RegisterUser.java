package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.expensetracker.Dbutils.UserInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterUser extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private Button register;
    UserInfo userInfo;
    AsyncResponse asyncResponse;
    Context context;
    public static String TAG = "register user";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        context = this;
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.useremail);
        register = (Button) findViewById(R.id.register);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty()) {
                    String message = "Username cannot be empty";
                    showAlertDialog(message);
                } else if (password.getText().toString().isEmpty()) {
                    String message = "Password cannot be empty";
                    showAlertDialog(message);
                } else if (email.getText().toString().isEmpty()) {
                    String message = "Password cannot be empty";
                    showAlertDialog(message);
                } else {

                    userInfo.insert_user(username.getText().toString(), password.getText().toString(), email.getText().toString(),
                            asyncResponse = new AsyncResponse() {
                                @Override
                                public void sendData(String data) {

                                    if (Integer.parseInt(data) == 0) {
                                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                        editor.putString("reg_token", refreshedToken);


                                        Intent intent = new Intent();
                                        intent.setClass(context, LoginUser.class);
                                        startActivity(intent);
                                    } else {
                                        String message = "Either username or email already exists with us. Please choose a new username or email";
                                        showAlertDialog(message);
                                    }
                                }
                            });
                }
            }
        });


        userInfo = new UserInfo();

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