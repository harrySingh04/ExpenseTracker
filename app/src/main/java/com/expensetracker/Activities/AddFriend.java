package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    public static String TAG = "Add friend";
    Context context;
    SharedPreferences sharedPreferences;
    String username;
    Integer userid;
    ProgressBar progressBar;

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
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e(TAG,String.valueOf(userid));
                progressBar.setVisibility(View.VISIBLE);
                friendsInfo.addfriend(userid, email.getText().toString(), username, asyncResponse = new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if(data.isEmpty()){

                                Log.e(TAG, "thisd is the data for add" + data);
                                Intent intent = new Intent();
                                intent.setClass(context, FriendsView.class);
                                startActivity(intent);

                        }

                       else if(Integer.parseInt(data) ==1){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("Email ID does not exist with us. Please check the Email ID");
                            builder1.setCancelable(true);
                            builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }


                            });
                            builder1.create().show();
                        }
                        else if(Integer.parseInt(data) ==2){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("The person is already in your Friend List");
                            builder1.setCancelable(true);
                            builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }


                            });
                            builder1.create().show();
                        }



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
        mDrawerList.setOnItemClickListener(new AddFriend.DrawerItemClickListener());

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


}