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
import android.view.Menu;
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
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Updatexpense extends AppCompatActivity {


    private EditText description;
    private Spinner categorySpinner;
    private Spinner groupNameSpinner;
    private EditText amount;
    private GroupInfo groupinfo;
    private Context context;
    private ArrayList<GroupModel> groupdetails;
    private Button update_expense;
    public static String TAG = "Add Expense";
    private ExpenseInfo expenseInfo;
    private EditText dp;
    private SharedPreferences sharedPreferences;
    private String GroupPosition, categoryPosition;
    private int id;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatexpense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        context = this;
        groupdetails = new ArrayList<GroupModel>();
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        update_expense = (Button) findViewById(R.id.add_expense);

        groupNameSpinner = (Spinner) findViewById(R.id.groupnameSpinner);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);

        dp = (EditText) findViewById(R.id.datepicker);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

       // adapter.getCount();
     //   int i = 0;







        update_expense.setText("Update");
        Bundle expenseData = getIntent().getExtras();
        String dataDescription = expenseData.getString("description");
        id = expenseData.getInt("id");
        int dataAmount = expenseData.getInt("amount");
        categoryPosition = expenseData.getString("category");
        GroupPosition = expenseData.getString("groupName");
        String date = expenseData.getString("date");



        Log.e(TAG,"adapter count"+adapter.getCount());
        for (int i=0;i<=adapter.getCount()-1;i++) {

            Log.e(TAG,"adapter"+i+adapter.getItem(i));
          //  Log.e(TAG,"category"+i+categoryPosition);

            if (adapter.getItem(i).equals(categoryPosition)) {
                categorySpinner.setSelection(i);
                break;
            }
            //     i++;
        }


        description.setText(dataDescription);
        amount.setText(String.valueOf(dataAmount));
        dp.setText(date);
        // categorySpinner.setSelection(categoryPosition);

//        groupNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // int group_id = ((GroupModel) groupNameSpinner.getSelectedItem()).getGroup_id();
//                //  Log.e(TAG, String.valueOf(group_id));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        groupinfo = new GroupInfo(context);

        groupinfo.getAllGroupsForUser(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    JSONArray main = new JSONArray(data);
                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);
                        String name = item.getString("name");
                        int group_id = item.getInt("group_id");

                        JSONObject userdetails = item.getJSONObject("UserModel");

                        ArrayList<UserModel> userModels = new ArrayList<UserModel>();

                        for (int j = 0;j<userdetails .length();j++) {
                            userModels.add(new UserModel(userdetails.getInt("user_id"),null,null));
                        }

                        groupdetails.add(new GroupModel(name, group_id,userModels));
                    }
                    groupdetails.add(new GroupModel("No group",null,null));

                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }


                ArrayAdapter GroupNameAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, groupdetails);
                GroupNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupNameSpinner.setAdapter(GroupNameAdapter);


                GroupNameAdapter.getCount();
                int i = 0;
                for (GroupModel g : groupdetails) {
                    if (g.getName().equals(GroupPosition)) {
                        groupNameSpinner.setSelection(i);
                        break;
                    }
                    i++;
                }


            }
        });


        update_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




              // strDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);

                String    strDate = dp.getText().toString();


//                Log.e(TAG, "I am here");
//                Log.e(TAG,String.valueOf(id));
//                Log.e(TAG,String.valueOf(amount.getText().toString()));
//                Log.e(TAG,strDate);
//                Log.e(TAG,String.valueOf( sharedPreferences.getInt("userid", 1)));
//                Log.e(TAG,description.getText().toString());
//                Log.e(TAG, categorySpinner.getSelectedItem().toString());
//                Log.e(TAG,String.valueOf(((GroupModel) groupNameSpinner.getSelectedItem()).getGroup_id()));


                //Integer.parseInt(amount.getText().toString())
                //description.getText().toString()
                Integer group_id = ((GroupModel) groupNameSpinner.getSelectedItem()).getGroup_id();
                expenseInfo.editexpense(id, Float.parseFloat(amount.getText().toString()), strDate,
                        sharedPreferences.getInt("userid", 1), description.getText().toString(),
                        categorySpinner.getSelectedItem().toString(), group_id, new AsyncResponse() {
                            @Override
                            public void sendData(String data) {

                            }
                        });

                Intent intent = new Intent();
                intent.setClass(context, Home.class);
                startActivity(intent);


            }
        });


        expenseInfo = new ExpenseInfo(context);

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context,position);
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
     //   Log.e("possssssssssssssss", String.valueOf(item));
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
        mDrawerList.setOnItemClickListener(new Updatexpense.DrawerItemClickListener());

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

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateYouChoosed = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            dp.setText(dateYouChoosed);

        }
    };


}

