package com.expensetracker.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.expensetracker.Dbutils.ExpenseInfo;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    private EditText description, amount, dp;
    private Spinner categorySpinner;
    private Spinner groupNameSpinner;
    private GroupInfo groupinfo;
    private AsyncResponse asyncResponse, add_data;
    private Context context;
    private ArrayList<GroupModel> groupdetails;
    private Button add_expense;
    public static String TAG = "Add Expense";
    private ExpenseInfo expenseInfo;

    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();
        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        context = this;
        userAuthentication();
        groupdetails = new ArrayList<GroupModel>();
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        add_expense = (Button) findViewById(R.id.add_expense);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        groupNameSpinner = (Spinner) findViewById(R.id.groupnameSpinner);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        expenseInfo = new ExpenseInfo();
        dp = (EditText) findViewById(R.id.datepicker);


        groupinfo = new GroupInfo();

        groupinfo.getAllGroupsForUser(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    JSONArray main = new JSONArray(data);
                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);
                        String name = item.getString("name");
                        int group_id = item.getInt("group_id");
                        int userid = item.getInt("user_id");
                        Log.e("name", name);
                        groupdetails.add(new GroupModel(name, userid, group_id));
                    }
                    groupdetails.add(new GroupModel("No group"));

                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }


                ArrayAdapter GroupNameAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, groupdetails);
                GroupNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupNameSpinner.setAdapter(GroupNameAdapter);
            }
        });


        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int day = dp.getDayOfMonth();
//                int month = dp.getMonth() + 1;
//                int year = dp.getYear();

                // String strDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);

                String strDate = dp.getText().toString();

                if (amount.getText().toString().isEmpty()) {

                } else if (description.getText().toString().isEmpty()) {

                } else {

                    Log.e(TAG, "I am here");
                    Integer group_id = ((GroupModel) groupNameSpinner.getSelectedItem()).getGroup_id();
                    expenseInfo.addexpense(Integer.parseInt(amount.getText().toString()), strDate, sharedPreferences.getInt("userid", 1), description.getText().toString(), categorySpinner.getSelectedItem().toString(), group_id,
                            add_data = new AsyncResponse() {
                                @Override
                                public void sendData(String data) {

                                }
                            });
                    Intent intent = new Intent();
                    intent.setClass(context, Home.class);
                    startActivity(intent);
                }
            }
        });

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePickerDialog(v);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay);
                dateDialog.show();


            }
        });


    }

    public void userAuthentication() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        if (sharedPreferences.getString("username", "").isEmpty()) {
            Intent intent = new Intent();
            intent.setClass(context, LoginUser.class);
            startActivity(intent);
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateYouChoosed = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            dp.setText(dateYouChoosed);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
      //  Log.e("possssssssssssssss", String.valueOf(item));
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
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
        mDrawerList.setOnItemClickListener(new AddExpense.DrawerItemClickListener());

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



    }






}
