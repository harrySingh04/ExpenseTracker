package com.expensetracker.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
    private Button groupExpense, pieChart, editgroup, deletegroup;
    private int groupid;
    private String groupname;
    TextView grpame;
    ProgressBar progressBar;
    GroupInfo groupInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        groupExpense = (Button) findViewById(R.id.groupexpense);
        pieChart = (Button) findViewById(R.id.piechart);
        editgroup = (Button) findViewById(R.id.editgroup);

        deletegroup = (Button) findViewById(R.id.deletegroup);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        grpame = (TextView) findViewById(R.id.group_name);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        groupInfo = new GroupInfo();

        usermodel = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        groupid = extras.getInt("groupid");
        groupname = extras.getString("groupname");
        grpame.setText(groupname);
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

    }

    @Override
    protected void onStart() {
        super.onStart();

        groupInfo.getgroupmembers(groupid, new AsyncResponse() {
            @Override
            public void sendData(String data) {
                Log.e(TAG, data);
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONArray main = new JSONArray(data);

                    Log.e("data", data);

                    for (int i = 0; i <= main.length() - 1; i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        String username = item.getString("username");
                        String email = item.getString("email");


                        Log.e(TAG, "I am in for loop");
                        usermodel.add(new UserModel(id, username, email));

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
                intent.putExtra("groupid", groupid);
                intent.putExtra("groupname", groupname);
                startActivity(intent);


            }
        });

        editgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, EditGroup.class);
                intent.putExtra("groupdetails", usermodel);
                intent.putExtra("groupname", groupname);
                intent.putExtra("groupid", groupid);
                startActivity(intent);
            }
        });

        deletegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Are you sure you want to delete this group?");
                builder.setTitle("Delete Group");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressBar.setVisibility(View.VISIBLE);


                        groupInfo.deletegroup(groupid, new AsyncResponse() {
                            @Override
                            public void sendData(String data) {
                                Intent intent = new Intent();
                                intent.setClass(context, Home.class);
                                startActivity(intent);
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

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


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new SingleGroupDetails.DrawerItemClickListener());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                Log.e(TAG, "ondrawer clossed");

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                Log.e(TAG, "ondrawer opened");

                invalidateOptionsMenu();
            }
        };


    }


}
