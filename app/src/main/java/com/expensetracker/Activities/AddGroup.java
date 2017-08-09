package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.expensetracker.Adapters.FriendsListAdapter;
import com.expensetracker.Dbutils.FriendsInfo;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddGroup extends AppCompatActivity {


    private RecyclerView friends_container;
    private EditText groupname;
    private Button Add;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserModel> userdetails = new ArrayList<UserModel>();
    Context context;
    ItemClickListener itemClickListener;
    public static String TAG = "Add Group";
    private FriendsInfo friendsInfo;
    ArrayList<Integer> userids = new ArrayList<>();
    SharedPreferences sharedPreferences;
    GroupInfo groupInfo;
    AsyncResponse userRegistered;
    AsyncResponse asyncResponse;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    TextView nofriendmessage,StaticContentforFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        groupname = (EditText) findViewById(R.id.groupName);
        Add = (Button) findViewById(R.id.addGroup);
        friends_container = (RecyclerView) findViewById(R.id.friendsList);
        layoutManager = new LinearLayoutManager(context);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE); //1
        context = this;
        groupInfo = new GroupInfo(context);
        nofriendmessage = (TextView) findViewById(R.id.nofriendmessage);
        StaticContentforFriends = (TextView)findViewById(R.id.StaticContentforFriends);

        friendsInfo = new FriendsInfo(context);
        friendsInfo.getallfriends(sharedPreferences.getInt("userid", 0), asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {

                Log.e(TAG, data);

                try {
                    JSONArray main = new JSONArray(data);

                    if (main.length() > 0) {

                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);
                            String username = item.getString("username");
                            String email = item.getString("email");
                            int userid = item.getInt("id");

                            userdetails.add(new UserModel(userid, username, email));
                        }


                        adapter = new FriendsListAdapter(userdetails, itemClickListener);
                        layoutManager = new LinearLayoutManager(context);
                        friends_container.setLayoutManager(layoutManager);
                        friends_container.setHasFixedSize(true);
                        friends_container.setAdapter(adapter);


                    } else {
                        nofriendmessage.setVisibility(View.VISIBLE);
                        StaticContentforFriends.setVisibility(View.INVISIBLE);
                        Add.setEnabled(false);
                        Add.setBackgroundColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                //     Log.e(TAG,String.valueOf(clickedItemIndex));
                if (userids != null) {
                    if (userids.contains(clickedItemIndex)) {
                        userids.remove(new Integer(clickedItemIndex));
                    } else if (!userids.contains(clickedItemIndex)) {
                        userids.add(new Integer(clickedItemIndex));
                    }
                }
            }
        };


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //    Log.e(TAG,String.valueOf(sharedPreferences.getInt("userid", 0)));
                userids.add(sharedPreferences.getInt("userid", 0));

                if (!groupname.getText().toString().isEmpty()) {
                    if (userids.size()>1) {


                        groupInfo.addGroupFromId(userids, groupname.getText().toString(), userRegistered = new AsyncResponse() {
                            @Override
                            public void sendData(String data) {

                            }
                        });

                        Intent intent = new Intent();
                        intent.putExtra("fragmentNumber",1);
                        intent.setClass(context, Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        String message = "You have not selected any friend";
                        showAlertDialog(message);
                    }
                }
                else{
                    String message = "Please enter the group Name";
                    showAlertDialog(message);
                }
            }
        });


    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context, position);
            // selectedItem();
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


    public void setLeftPane() {


        //  mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);
//        setLeftPane();
        // set a custom shadow that overlays the main content when the drawer opens
        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new AddGroup.DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                Log.e(TAG, "ondrawer clossed");
                // getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                Log.e(TAG, "ondrawer opened");
                //   getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //     mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            //   selectItem(0);
//        }


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