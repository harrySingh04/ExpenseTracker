package com.expensetracker.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Summary extends AppCompatActivity {


    // Button submit;
    private RecyclerView expense_container;
    private ExpenseAdapter adapter;
    private LinearLayoutManager layoutManager;
    TextView from, to;
    Spinner groupNameSpinner;
    public static String TAG = "summary";
    private GroupInfo groupinfo;
    SharedPreferences sharedPreferences;
    ArrayList<GroupModel> groupdetails;
    Context context;
    ArrayList<ExpenseModel> expenseModel;

    ProgressBar progressBar;
    String groupName;
    private PieChart mchart;
    TextView nopiecharttext;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String navigationItems[];
    private ActionBarDrawerToggle mDrawerToggle;
    TextView noDataMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        //  submit = (Button) findViewById(R.id.submit);

        groupNameSpinner = (Spinner) findViewById(R.id.groupSpinner);
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        context = this;
        groupdetails = new ArrayList<>();
        from = (TextView) findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);
        groupinfo = new GroupInfo(context);
        this.expenseModel = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        nopiecharttext = (TextView) findViewById(R.id.nopiechart);
        mchart = (PieChart) findViewById(R.id.chart);
        expense_container = (RecyclerView) findViewById(R.id.expense_container);
        noDataMessage = (TextView) findViewById(R.id.noDataMessage);


        final GroupInfo expenseInfo = new GroupInfo(context);
        expenseInfo.getAllGroupExpensesForUser(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONArray main = new JSONArray(data);

                    //     Log.e(TAG, "data coming from network call" + data);

                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);

                        int expense_id = item.getInt("id");
                        String amount = item.getString("amount");
                        String date = item.getString("date");
                        String description = item.getString("description");
                        String category = item.getString("category");

                        JSONObject usdetails = item.getJSONObject("userDetails");
                        JSONObject groupDetails = item.getJSONObject("groupDetails");
                        //       Log.e("name", name);

                        UserModel userModel = new UserModel(usdetails.getInt("id"), usdetails.getString("username"), usdetails.getString("email"));
                        GroupModel groupModel = new GroupModel(groupDetails.getInt("group_id"), groupDetails.getString("name"));

                        expenseModel.add(new ExpenseModel(expense_id, Float.valueOf(amount), date, description, category, userModel, groupModel));

//                        for (ExpenseModel e : expenseModel) {
//                            Log.e(TAG, e.getDate());
//                        }
//
//                        Log.e(TAG, "0" + groupDetails.getString("name"));

                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


//                adapter = new ExpenseAdapter(expenseModel, expenseData);
//                layoutManager = new LinearLayoutManager(context);
//                expense_container.setLayoutManager(layoutManager);
//                expense_container.setHasFixedSize(true);
//                expense_container.setAdapter(adapter);

            }
        });


        groupinfo.getAllGroupsForUser(sharedPreferences.getInt("userid", 0), new AsyncResponse() {
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

                        for (int j = 0; j < userdetails.length(); j++) {
                            userModels.add(new UserModel(userdetails.getInt("user_id"), null, null));
                        }

                        groupdetails.add(new GroupModel(name, group_id, userModels));
                    }
                    groupdetails.add(new GroupModel("No group", null, null));

                    //        Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    //          Log.e("oiasdha", "lskdkj", e);
                }


                ArrayAdapter GroupNameAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, groupdetails);
                GroupNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupNameSpinner.setAdapter(GroupNameAdapter);
                groupName = ((GroupModel) groupNameSpinner.getSelectedItem()).getName();
            }
        });


//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, String.valueOf(from));
//                Log.e(TAG, String.valueOf(to));
//
//                groupName = ((GroupModel) groupNameSpinner.getSelectedItem()).getName();
//
//
//                displayPiechart();
//
//            }
//        });

        from.setOnClickListener(new View.OnClickListener() {
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


        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePickerDialog(v);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(context, datePickerListener2, mYear, mMonth, mDay);
                dateDialog.show();

            }
        });


        groupNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupName = ((GroupModel) groupNameSpinner.getSelectedItem()).getName();
                displayPiechart();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateYouChoosed = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            from.setText(dateYouChoosed);
            displayPiechart();
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateYouChoosed = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            to.setText(dateYouChoosed);
            displayPiechart();
        }
    };


    public void displayPiechart() {

        ArrayList<ExpenseModel> PieChartData = new ArrayList<>();


        try {
            if (from.getText().toString() != null && !from.getText().toString().isEmpty()) {
                if (to.getText().toString() != null && !to.getText().toString().isEmpty()) {
                    if (groupName != null) {

                        noDataMessage.setVisibility(View.GONE);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fromdate = sdf.parse(from.getText().toString());
                        Date todate = sdf.parse(to.getText().toString());

                        for (ExpenseModel e : this.expenseModel) {
                            if ((sdf.parse(e.getDate()).before(todate) && sdf.parse(e.getDate()).after(fromdate))
                                    || sdf.parse(e.getDate()).equals(todate) || sdf.parse(e.getDate()).equals(fromdate)) {
                                if (e.getGroupDetails().getName().equals(groupName)) {
                                    PieChartData.add(new ExpenseModel(e.getId(), e.getAmount(), e.getDate(), e.getDescription(), e.getCategory(), e.getUserDetails(), e.getGroupDetails()));
                                }
                            }
                        }


                        if (this.expenseModel != null) {

                            mchart.setVisibility(View.VISIBLE);
                            nopiecharttext.setVisibility(View.GONE);


//                            This point i need new arryalist

                            setAdapterForSingleGroup(PieChartData);


                            PieChartData = processData(PieChartData);

                            if (PieChartData != null && !PieChartData.isEmpty()) {
                                mchart.setVisibility(View.VISIBLE);
                                nopiecharttext.setVisibility(View.GONE);
                                displayPieChart(PieChartData);
                            } else {
                                Log.e(TAG, "in else");
                                mchart.setVisibility(View.GONE);
                                nopiecharttext.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.e(TAG, "in second else");
                            mchart.setVisibility(View.GONE);
                            nopiecharttext.setVisibility(View.VISIBLE);
                        }


                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }


    public void displayPieChart(ArrayList<ExpenseModel> eModel) {


        //Pie chart referring to the view

        //Apply Styling
        mchart.getDescription().setEnabled(false);
        mchart.setHoleRadius(54f);
        mchart.setTransparentCircleRadius(57f);
        mchart.setUsePercentValues(true);
        mchart.setCenterText(groupName);
        //mchart.setExtraOffsets(55, 10, 10, 10);
        mchart.animateY(900);

        //Enable Rotation of the chart by touch
        mchart.setRotationAngle(0);
        mchart.setRotationEnabled(true);
        mchart.setEntryLabelColor(Color.BLACK);

        mchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                ArrayList<ExpenseModel> dataForAdapter = processDataForAdapter(((ExpenseModel) e.getData()).getUserDetails().getUser_id());


                if (adapter != null) {
                    adapter.swapCursor(dataForAdapter);
                }


//                for (ExpenseModel expense : dataForAdapter) {
//                    Log.e(TAG, "amount" + String.valueOf(expense.getUsermodel().getUser_id()));
//                    Log.e(TAG, "amount" + String.valueOf(expense.getAmount()));
//                }


            }

            @Override
            public void onNothingSelected() {

//                Log.e(TAG,"In nothing seletced");
//
//                if (expenseModel != null) {
//                    adapter = new ExpenseAdapter(expenseModel, new ExpenseData() {
//                        @Override
//                        public void expenseDetails(int id, String description, int amount, String date, String category, String groupName) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, Updatexpense.class);
//
//                            intent.putExtra("id", id);
//                            intent.putExtra("description", description);
//                            intent.putExtra("date", date);
//                            intent.putExtra("category", category);
//                            intent.putExtra("groupName", groupName);
//                            intent.putExtra("amount", amount);
//
//                            Log.e(TAG, "id is" + id);
//                            startActivity(intent);
//
//                        }
//                    });
//                    layoutManager = new LinearLayoutManager(context);
//                    expense_container.setLayoutManager(layoutManager);
//                    expense_container.setHasFixedSize(true);
//                    expense_container.setAdapter(adapter);
//                }
//
//
//


            }
        });

        addData(eModel);
        Legend l = mchart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setTextSize(13f);
//        l.setDrawInside(false);
//        l.setYEntrySpace(2f);
//        l.setYOffset(10f);

        l.setEnabled(false);


    }

    // Method to add Data into the Pie chart
    private void addData(ArrayList<ExpenseModel> eModel) {
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i <= eModel.size() - 1; i++) {
            Log.e(TAG, eModel.get(i).getUserDetails().getUsername());
            Log.e(TAG, String.valueOf(eModel.get(i).getAmount()));
            yVals.add(new PieEntry(eModel.get(i).getAmount(), eModel.get(i).getUserDetails().getUsername(),
                    eModel.get(i)
            ));

        }

        //Create Pie Data Set
        PieDataSet dataSet = new PieDataSet(yVals, "Expense Tracker");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextSize(30f);

        //Add Many Colors

        ArrayList<Integer> colors = new ArrayList<Integer>();


        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //Instantiate Pie Data object now

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);


        mchart.setData(data);


        mchart.invalidate();


    }

    public ArrayList<ExpenseModel> processData(ArrayList<ExpenseModel> expenseModellocal) {


        for (ExpenseModel e : expenseModel) {
            Log.e(TAG, "line 1amount" + String.valueOf(e.getAmount()));
        }


        for (int i = 0; i <= expenseModellocal.size() - 1; i++) {
            for (int j = i + 1; j <= expenseModellocal.size() - 1; j++) {
                if (expenseModellocal.get(i).getUserDetails().getUser_id() == expenseModellocal.get(j).getUserDetails().getUser_id()) {
                    expenseModellocal.get(i).setAmount(expenseModellocal.get(i).getAmount() + expenseModellocal.get(j).getAmount());
                    expenseModellocal.remove(expenseModellocal.get(j));
                    j -= 1;
                }
            }
        }

        for (ExpenseModel e : expenseModel) {
            Log.e(TAG, "line 2amount" + String.valueOf(e.getAmount()));
        }


        return expenseModellocal;
    }


    public ArrayList<ExpenseModel> processDataForAdapter(int userID) {

        ArrayList<ExpenseModel> dataForAdapter = new ArrayList<ExpenseModel>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fromdate = sdf.parse(from.getText().toString());
            Date todate = sdf.parse(to.getText().toString());

            for (ExpenseModel e : this.expenseModel) {
                if ((sdf.parse(e.getDate()).before(todate) && sdf.parse(e.getDate()).after(fromdate))
                        || sdf.parse(e.getDate()).equals(todate) || sdf.parse(e.getDate()).equals(fromdate)) {
                    if (e.getGroupDetails().getName().equals(groupName)) {
                        if (e.getUserDetails().getUser_id() == userID) {
                            dataForAdapter.add(e);
                        }
                    }
                }
            }


        } catch (Exception e) {
            Log.e(TAG, "errpr", e);
        }

        return dataForAdapter;

    }

    public void setAdapterForSingleGroup(ArrayList<ExpenseModel> expenseModel) {


        if (expenseModel != null) {
            adapter = new ExpenseAdapter(expenseModel, new ExpenseData() {
                @Override
                public void expenseDetails(int id, String description, Float amount, String date, String category, String groupName) {
                }
            });
            layoutManager = new LinearLayoutManager(context);
            expense_container.setLayoutManager(layoutManager);
            expense_container.setHasFixedSize(true);
            expense_container.setAdapter(adapter);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expense_container.getContext(),
                    layoutManager.getOrientation());
            expense_container.addItemDecoration(dividerItemDecoration);
        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        mDrawerList.setOnItemClickListener(new Summary.DrawerItemClickListener());

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

