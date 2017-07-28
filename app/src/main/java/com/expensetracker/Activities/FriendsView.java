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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Adapters.FriendsAdapter;
import com.expensetracker.Dbutils.FriendsInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsView extends AppCompatActivity {


    private RecyclerView friendsView;
    private FriendsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserModel> userdetails = new ArrayList<UserModel>();
    Context context;
    ItemClickListener itemClickListener;
    FriendsInfo friendsInfo;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    SharedPreferences sharedPreferences;
    public static String TAG = "GroupView";
    private ProgressBar progressBar;
    private TextView staticnofriendtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);


        context = this;
        setLeftPane();
        friendsView = (RecyclerView) findViewById(R.id.friendsrecyclerview);
        layoutManager = new LinearLayoutManager(context);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        staticnofriendtext = (TextView) findViewById(R.id.nofriendmessage);
        friendsInfo = new FriendsInfo();
        friendsInfo.getallfriends(sharedPreferences.getInt("userid", 0), new AsyncResponse() {
            @Override
            public void sendData(String data) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("in async response", data);

                try {
                    JSONArray main = new JSONArray(data);

                    if (main.length() != 0) {

                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);

                            int id = item.getInt("id");
                            String name = item.getString("username");
                            String email = item.getString("email");

                            userdetails.add(new UserModel(id, name, email));
                        }

                        for (UserModel u : userdetails) {
                            Log.e(TAG, String.valueOf("username" + u.getUsername()));
                        }

                        adapter = new FriendsAdapter(userdetails, itemClickListener);
                        layoutManager = new LinearLayoutManager(context);
                        friendsView.setLayoutManager(layoutManager);
                        friendsView.setHasFixedSize(true);
                        friendsView.setAdapter(adapter);
                    } else {
                        staticnofriendtext.setVisibility(View.VISIBLE);
                    }

                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {

            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent();
                intent.setClass(context, AddFriend.class);
                startActivity(intent);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int friendid = (int) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + friendid);
                SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
                final int userid = sharedPreferences.getInt("userid", 0);
                new FriendsInfo().deletefriend(userid, friendid, new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
//                        Intent intent = new Intent();
//                        intent.setClass(context, Home.class);
//                        startActivity(intent);

                        for(UserModel u:userdetails){
                            if(u.getUser_id()==friendid){
                                userdetails.remove(u);
                                adapter.swapCursor(userdetails);
                                break;
                            }
                        }

                        if(userdetails.isEmpty()){

                            staticnofriendtext.setVisibility(View.VISIBLE);

                        }

                    }
                });




            }
        }).attachToRecyclerView(friendsView);


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
        return super.onOptionsItemSelected(item);
    }


    public void setLeftPane() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new FriendsView.DrawerItemClickListener());


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

