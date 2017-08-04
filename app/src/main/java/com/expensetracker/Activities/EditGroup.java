package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ListView;

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

public class EditGroup extends AppCompatActivity {

    private RecyclerView friends_container;
    private EditText groupname;
    private Button Add;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserModel> frienddetails = new ArrayList<UserModel>();
    Context context;
    ItemClickListener itemClickListener;
    public static String TAG = "Edit Group";
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
    ArrayList<UserModel> groupdetails;
    String grpname;
    int groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        //setLeftPane();

        groupname = (EditText) findViewById(R.id.groupName);
        Add = (Button) findViewById(R.id.addGroup);
        Add.setText("Update");
        friends_container = (RecyclerView) findViewById(R.id.friendsList);
        layoutManager = new LinearLayoutManager(context);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE); //1
        context = this;
        groupdetails = new ArrayList<>();
        Intent intent = getIntent();
        groupdetails = (ArrayList<UserModel>) intent.getSerializableExtra("groupdetails");
        grpname = intent.getStringExtra("groupname");
        groupid = intent.getIntExtra("groupid", 0);
        groupname.setText(grpname);

        groupInfo = new GroupInfo();


        friendsInfo = new FriendsInfo();
        friendsInfo.getallfriends(sharedPreferences.getInt("userid", 0), asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {


                Log.e(TAG, "data beibng reciebved"+data);

                try {
                    JSONArray main = new JSONArray(data);

                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);
                        String username = item.getString("username");
                        String email = item.getString("email");
                        int userid = item.getInt("id");

                        frienddetails.add(new UserModel(userid, username, email));
                    }

                    for (UserModel g : frienddetails) {
                        Log.e("name of group", g.getUsername());
                    }


                    adapter = new FriendsListAdapter(frienddetails, groupdetails, itemClickListener);
                    layoutManager = new LinearLayoutManager(context);
                    friends_container.setLayoutManager(layoutManager);
                    friends_container.setHasFixedSize(true);
                    friends_container.setAdapter(adapter);


                } catch (Exception e) {
                    Log.e(TAG, "error", e);
                }
            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                if (userids != null) {
                    if (userids.contains(clickedItemIndex)) {
                        userids.remove(new Integer(clickedItemIndex));
                    } else if (!userids.contains(clickedItemIndex)) {
                        userids.add(new Integer(clickedItemIndex));
                    }
                }

                for (Integer i : userids) {
                    Log.e(TAG, "user id is" + i);
                }


            }
        };


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, String.valueOf(sharedPreferences.getInt("userid", 0)));
                userids.add(sharedPreferences.getInt("userid", 0));

//                groupInfo.updateGroup(userids, groupname.getText().toString(), userRegistered = new AsyncResponse() {
//                    @Override
//                    public void sendData(String data) {
//
//                    }
//                });

                groupInfo.updateGroup(groupid, userids, groupname.getText().toString(), userRegistered = new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
                        Intent intent = new Intent();
                        intent.setClass(context, GroupView.class);
                        startActivity(intent);
                    }
                });
//

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
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       // mDrawerToggle.onConfigurationChanged(newConfig);
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
        mDrawerList.setOnItemClickListener(new EditGroup.DrawerItemClickListener());

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
