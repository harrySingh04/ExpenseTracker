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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Dbutils.ExpenseInfo;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleGroupExpense extends AppCompatActivity {

    String groupName;
    int groupID;
    public static String TAG = "SingleGroupExpense";

    private RecyclerView expense_container;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<ExpenseModel>();
    private final Context context = this;
    private ProgressBar progressBar;
    ItemClickListener itemClickListener;
    ExpenseData expenseData;

    private FloatingActionButton button;
    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    TextView noDataMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group_expense);

        Intent intent = getIntent();
        groupID = intent.getIntExtra("groupid", 0);
        groupName = intent.getStringExtra("groupname");
        expense_container = (RecyclerView) findViewById(R.id.expense_container);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        noDataMessage = (TextView) findViewById(R.id.noDataMessage);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        GroupInfo groupInfo = new GroupInfo(context);

        groupInfo.getGroupExpense(groupID, new AsyncResponse() {
            @Override
            public void sendData(String data) {
                progressBar.setVisibility(View.INVISIBLE);


                try {
                    JSONArray main = new JSONArray(data);



                    Log.e(TAG, "data of expense" + String.valueOf(data));

                    if (main.length() > 0) {
                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);

                            int id = item.getInt("id");
                            String amount = item.getString("amount");
                            String date = item.getString("date");
                            String description = item.getString("description");
                            String category = item.getString("category");

                            JSONObject userDetails = item.getJSONObject("userDetails");
                            int userID = userDetails.getInt("id");
                            String username = userDetails.getString("username");
                            String email = userDetails.getString("email");

                            UserModel userModel = new UserModel(userID, username, email);

                            GroupModel groupModel = new GroupModel( groupID,groupName);
//                        if(item.optBoolean("groupName")){
//
//                        }

                            expenseModel.add(new ExpenseModel(id, Float.valueOf(amount), date, category, description , userModel,groupModel));

//                        for (ExpenseModel e : expenseModel) {
//                            Log.e(e.getDate(), e.getDate());
//                        }
                            //    Log.e("amount", String.valueOf(amount));
                        }

                    } else {
                        noDataMessage.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


                adapter = new ExpenseAdapter(expenseModel, expenseData);
                layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                expense_container.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expense_container.getContext(),
                        layoutManager.getOrientation());
                expense_container.addItemDecoration(dividerItemDecoration);
                expense_container.setHasFixedSize(true);
                expense_container.setAdapter(adapter);

            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {


            }
        };

        expenseData = new ExpenseData() {

            @Override
            public void expenseDetails(int id, String description, Float amount, String date, String category, String groupName) {
                Intent intent = new Intent();
                intent.setClass(context, Updatexpense.class);

                intent.putExtra("id", id);
                intent.putExtra("description", description);
                intent.putExtra("date", date);
                intent.putExtra("category", category);
                intent.putExtra("groupName", groupName);
                intent.putExtra("amount", amount);

                Log.e(TAG, "id is" + id);
                // startActivity(intent);

            }
        };


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + id);


                new ExpenseInfo(context).deleteexpense(id, new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
                        Intent intent = new Intent();
                        intent.setClass(context, Home.class);
                        startActivity(intent);
                    }
                });


            }
        }).attachToRecyclerView(expense_container);


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
        mDrawerList.setOnItemClickListener(new SingleGroupExpense.DrawerItemClickListener());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent();
        i.setClass(context, SingleGroupDetails.class);

        i.putExtra("groupname", groupName);
        i.putExtra("groupid", groupID);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

}
