package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Adapters.PieChartAdapter;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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

import java.util.ArrayList;

import static com.github.mikephil.charting.components.Legend.LegendForm.SQUARE;


/**
 * Created by Harminder on 7/21/2017.
 */
// Creation of new Activity by Harminder to display Pie Chart for Expense Tracker
public class PieChartExpense extends AppCompatActivity {


    //Variable for the Pie Chart variable
    private PieChart mchart;
    private int groupID;
    private String groupName;
    ArrayList<ExpenseModel> expenseModel;
    public static String TAG = "PieChartExpense";
    ProgressBar progressBar;
    RecyclerView pieRecycler;
    PieChartAdapter pieChartAdapter;
    ExpenseAdapter expenseAdapter;
    Context context;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String navigationItems[];
    private ActionBarDrawerToggle mDrawerToggle;
    TextView noPieChartMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        expenseModel = new ArrayList<>();
        Intent intent = getIntent();
        groupID = intent.getIntExtra("groupid", 0);
        groupName = intent.getStringExtra("groupname");
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        context = this;
        pieRecycler = (RecyclerView) findViewById(R.id.expense_container);
        pieRecycler.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pieRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(pieRecycler.getContext(),
                layoutManager.getOrientation());
        pieRecycler.addItemDecoration(dividerItemDecoration);
        noPieChartMessage = (TextView) findViewById(R.id.noPieChartMessage);

        GroupInfo groupInfo = new GroupInfo(context);
        groupInfo.getGroupExpense(groupID, new AsyncResponse() {
            @Override
            public void sendData(String data) {
                progressBar.setVisibility(View.INVISIBLE);


                try {
                    JSONArray main = new JSONArray(data);

                    if(main.length()>0){



                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        int amount = item.getInt("amount");
                        String date = item.getString("date");
                        String description = item.getString("description");
                        String category = item.getString("category");

                        JSONObject userDetails = item.getJSONObject("userDetails");
                        int userID = userDetails.getInt("id");
                        String username = userDetails.getString("username");
                        String email = userDetails.getString("email");

                        UserModel userModel = new UserModel(userID, username, email);

                        expenseModel.add(new ExpenseModel(id, groupID, amount, date, category, description, groupName, userModel));

                    }

                    ArrayList<ExpenseModel> dataForAdapter;
                    dataForAdapter = processData();

                    //   Log.e(TAG,"2size of datafor adapter"+String.valueOf(dataForAdapter.size()));

                    displayPieChart(dataForAdapter);
                    expenseAdapter = new ExpenseAdapter(expenseModel, new ExpenseData() {
                        @Override
                        public void expenseDetails(int id, String description, int amount, String date, String category, String groupName) {
                        }
                    });
                    pieRecycler.setAdapter(expenseAdapter);

                }
                else{
                    noPieChartMessage.setVisibility(View.VISIBLE);
                }

                } catch (Exception e) {
                    Log.e("error", "error", e);
                }
            }
        });


    }

    public void displayPieChart(ArrayList<ExpenseModel> dataForAdapter) {
        //Pie chart referring to the view
        mchart = (PieChart) findViewById(R.id.chart);
        //Apply Styling
        //Pie chart referring to the view

        //Apply Styling
        mchart.getDescription().setEnabled(false);
        mchart.setHoleRadius(54f);
        mchart.setTransparentCircleRadius(57f);
        mchart.setUsePercentValues(true);
        mchart.setCenterText(groupName);
        mchart.setExtraOffsets(0, 0, 60, 10);
        mchart.animateY(900);



        //Enable Rotation of the chart by touch
        mchart.setRotationAngle(0);
        mchart.setRotationEnabled(true);
        mchart.setEntryLabelColor(Color.BLACK);

        mchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                ArrayList<ExpenseModel> dataForAdapter = processDataForAdapter(((ExpenseModel) e.getData()).getUsermodel().getUser_id());
                if (expenseAdapter != null) {
                    expenseAdapter.swapCursor(dataForAdapter);
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });

        addData(dataForAdapter);


//        Log.e(TAG, String.valueOf(l.getForm()));
//        Log.e(TAG, String.valueOf(l.getFormLineDashEffect()));
        //    l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "Set1", "Set2", "Set3", "Set4", "Set5" });

        //   l.setEnabled(false);

    }

    // Method to add Data into the Pie chart
    private void addData(ArrayList<ExpenseModel> dataForAdapter) {
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i <= dataForAdapter.size() - 1; i++) {
            yVals.add(new PieEntry(dataForAdapter.get(i).getAmount(), dataForAdapter.get(i).getUsermodel().getUsername(), dataForAdapter.get(i)));


        }

        //Create Pie Data Set
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextSize(30f);

        //Add Many Colors

        ArrayList<Integer> colors = new ArrayList<Integer>();
//        Integer color1 = Color.parseColor("#BA68C8");
//        Integer color5 = Color.parseColor("#81D4FA");
//        Integer color7 = Color.parseColor("#F44336");
//        Integer color3 = Color.parseColor("#64B5F6");
//        Integer color4 = Color.parseColor("#FFEB3B");
//        Integer color6 = Color.parseColor("#7986CB");
//        Integer color8 = Color.parseColor("#CDDC39");
//        Integer color2 = Color.parseColor("#2196F3");
//
//        colors.add(color8);
//        colors.add(color6);
//        colors.add(color5);
//        colors.add(color4);
//        colors.add(color2);
//        colors.add(color3);
//        colors.add(color7);
//        colors.add(color1);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

     //   dataSet.getColor(0);

        //Instantiate Pie Data object now

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);


//        ArrayList<ExpenseModel> dataForAdapter;
//        dataForAdapter = processData();


        setLegend(dataForAdapter);
        mchart.setData(data);
        mchart.invalidate();


    }

    public ArrayList<ExpenseModel> processData() {

//        for (ExpenseModel e : expenseModel) {
//            Log.e(TAG,"1in  processData"+ String.valueOf(e.getAmount()));
//        }

        ArrayList<ExpenseModel> copy = new ArrayList<>();

        for (ExpenseModel e : expenseModel) {
            copy.add(new ExpenseModel(e.getId(), e.getAmount(), e.getDate(), e.getDescription(), e.getCategory(), e.getUsermodel(), e.getGroupModel()));
        }

        for (int i = 0; i <= copy.size() - 1; i++) {
            for (int j = i + 1; j <= copy.size() - 1; j++) {
                if (copy.get(i).getUsermodel().getUser_id() == copy.get(j).getUsermodel().getUser_id()) {
                    copy.get(i).setAmount(copy.get(i).getAmount() + copy.get(j).getAmount());
                    copy.remove(copy.get(j));
                    j -= 1;
                }
            }
        }

        for (ExpenseModel e : copy) {
            Log.e(TAG, "1in  processData" + String.valueOf(e.getAmount()));
        }

        return copy;
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
        mDrawerList.setOnItemClickListener(new PieChartExpense.DrawerItemClickListener());

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


    public ArrayList<ExpenseModel> processDataForAdapter(int userID) {

//        Log.e(TAG,String.valueOf("in processDataForAdapter"));
//        Log.e(TAG,String.valueOf("bla bla bla"));
//        Log.e(TAG,"size is"+String.valueOf(expenseModel.size()));

//        for(ExpenseModel e:expenseModel){
//            Log.e(TAG,String.valueOf(e.getAmount()));
//        }

        ArrayList<ExpenseModel> dataForAdapter = new ArrayList<ExpenseModel>();
        try {
            for (ExpenseModel e : this.expenseModel) {
                if (e.getUsermodel().getUser_id() == userID) {
                    dataForAdapter.add(e);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }

        return dataForAdapter;

    }

    public void setLegend(ArrayList<ExpenseModel> expenseModel) {

        ArrayList<LegendEntry> legendEntries = new ArrayList<LegendEntry>();

        for(ExpenseModel e:expenseModel){

            String data = e.getUsermodel().getUsername()+":"+e.getAmount();

            legendEntries.add(new LegendEntry(data, SQUARE, 10, 10, null, Color.BLACK));


        }

//        legendEntries.add(new LegendEntry("abc", SQUARE, 10, 10, null, Color.BLACK));
//        legendEntries.add(new LegendEntry("cbf", SQUARE, 10, 10, null, Color.RED));

        Legend l = mchart.getLegend();
        //    Legend l = new Legend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(20f);
        l.setDrawInside(false);
        l.setYEntrySpace(2f);
        l.setXOffset(10f);
        l.setCustom(legendEntries);

    }


}


