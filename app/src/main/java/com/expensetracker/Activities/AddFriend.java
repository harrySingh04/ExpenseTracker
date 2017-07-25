package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.expensetracker.Dbutils.FriendsInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.MenuPane;
import com.expensetracker.R;

public class AddFriend extends AppCompatActivity {

    private EditText email;
    private Button addFriend;
    private FriendsInfo friendsInfo;
    private AsyncResponse asyncResponse;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    public static String TAG = "Home";
    Context context;
    SharedPreferences sharedPreferences;
    String username;
    Integer userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setLeftPane();

        email = (EditText) findViewById(R.id.useremail);
        addFriend = (Button) findViewById(R.id.add);
        context = this;
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userid = sharedPreferences.getInt("userid", 0);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,String.valueOf(userid));
                friendsInfo.addfriend(userid, email.getText().toString(), username, asyncResponse = new AsyncResponse() {
                    @Override
                    public void sendData(String data) {

                        Intent intent = new Intent();
                        intent.setClass(context,FriendsView.class);
                        startActivity(intent);


                    }
                });


            }
        });


        friendsInfo = new FriendsInfo();


    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context, position);
            // selectedItem();
        }
    }

    public void selectedItem(int position) {

        switch (position) {

            case 0:
                Log.e(TAG, "Item 1");
                break;

            case 1:
                Log.e(TAG, "Item 2");
                break;

            case 2:
                Log.e(TAG, "Item 3");
                break;

            case 3:
                Log.e(TAG, "Item 4");
                break;

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        Log.e("possssssssssssssss", String.valueOf(item));
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }