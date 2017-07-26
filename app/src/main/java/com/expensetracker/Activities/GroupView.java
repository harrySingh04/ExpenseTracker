package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;

import com.expensetracker.Adapters.GroupAdapter;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.GroupData;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupView extends AppCompatActivity {


    private RecyclerView group_container;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GroupModel> groupdetails = new ArrayList<GroupModel>();
    Context context;
    GroupData itemClickListener;
    public static String TAG = "GroupView";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        Log.e("in groupview", "in groupview");
        context = this;
        setLeftPane();
        group_container = (RecyclerView) findViewById(R.id.single_group_container);
        layoutManager = new LinearLayoutManager(context);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);


        GroupInfo groupInfo = new GroupInfo();
        groupInfo.getAllGroupsForUser(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                Log.e("in async response", data);

                try {
                    JSONArray main = new JSONArray(data);

                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);
                        String name = item.getString("name");
                        int group_id = item.getInt("group_id");
                        int userid = item.getInt("user_id");
                        Log.e("name", name);

                        groupdetails.add(new GroupModel(name,userid,group_id));
                    }

                    for (GroupModel g : groupdetails) {
                        Log.e("name of group", g.getName());
                    }


                    adapter = new GroupAdapter(groupdetails, new GroupData() {
                        @Override
                        public void groupDetails(int id, String groupName) {
                            //    Log.e(TAG,String.valueOf(clickedItemIndex));
                            Intent intent = new Intent();
                            intent.setClass(context, SingleGroupDetails.class);
                            intent.putExtra("groupid", id);
                            intent.putExtra("groupname", groupName);
                            context.startActivity(intent);

                        }
                    });
                    layoutManager = new LinearLayoutManager(context);
                    group_container.setLayoutManager(layoutManager);
                    group_container.setHasFixedSize(true);
                    group_container.setAdapter(adapter);


                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        });


        FloatingActionButton add_group = (FloatingActionButton) findViewById(R.id.add_group);


        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent();
                intent.setClass(context, AddGroup.class);
                startActivity(intent);


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    public void setLeftPane() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new GroupView.DrawerItemClickListener());

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

