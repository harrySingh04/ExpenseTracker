package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Adapters.SingleGroupMemberAdapter;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleGroupDetails extends AppCompatActivity {
    public static String TAG = "SingleGroupDetails";
    ArrayList<UserModel> usermodel;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private ItemClickListener itemClickListener;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    private Button groupExpense, pieChart;
    private int groupid;
    private String groupname;
    TextView grpame;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        usermodel = new ArrayList<UserModel>();
        groupExpense = (Button) findViewById(R.id.groupexpense);
        pieChart = (Button) findViewById(R.id.piechart);
        context = this;
        setLeftPane();

     //   Log.e(TAG,"value of groupname"+groupname);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        grpame = (TextView) findViewById(R.id.group_name);
        progressBar = (ProgressBar)findViewById(R.id.progressbar) ;
        progressBar.setVisibility(View.VISIBLE);




        Bundle extras = getIntent().getExtras();
        groupid = extras.getInt("groupid");
        groupname = extras.getString("groupname");
        Log.e(TAG,"value of group name"+groupname);
        grpame.setText(groupname);
     //   Log.e(TAG, "value of id is: " + String.valueOf(groupid));

        GroupInfo groupInfo = new GroupInfo();

        groupInfo.getgroupmembers(groupid, new AsyncResponse() {
            @Override
            public void sendData(String data) {
                Log.e(TAG, data);
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONArray main = new JSONArray(data);

//                    String name = main.getString("id");
//                    String id = main.getString("username");
//                    String email = main.getString("email");
                    //     JSONArray items = main.getJSONArray("");

                    Log.e("data", data);

                    for (int i = 0; i <= main.length() - 1; i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        String username = item.getString("username");
                        String email = item.getString("email");


                        Log.e(TAG, "I am in for loop");
                        usermodel.add(new UserModel(id, username, email));

//                        for (UserModel u : usermodel) {
//                            Log.e(e.getDate(), e.getDate());
//                        }


                        adapter = new SingleGroupMemberAdapter(usermodel, itemClickListener);
                        layoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);


                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        groupExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, SingleGroupExpense.class);

                Log.e(TAG, groupname);
                Log.e(TAG, "group id is : " + String.valueOf(groupid));

                intent.putExtra("groupid", groupid);
                intent.putExtra("groupname", groupname);
                startActivity(intent);


            }
        });

        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, PieChartExpense.class);

                Log.e(TAG, groupname);
                Log.e(TAG, "group id is : " + String.valueOf(groupid));

                intent.putExtra("groupid", groupid);
                intent.putExtra("groupname", groupname);
                startActivity(intent);


            }
        });

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context, position);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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
        mDrawerList.setOnItemClickListener(new SingleGroupDetails.DrawerItemClickListener());

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
