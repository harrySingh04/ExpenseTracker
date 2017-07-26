package com.expensetracker.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Model.ExpenseModel;
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

import java.util.ArrayList;

/**
 * Created by Harminder on 7/21/2017.
 */
// Creation of new Activity by Harminder to display Pie Chart for Expense Tracker
public class PieChartExpense extends AppCompatActivity {

    //Private varibles yData to store the value of Expenses on which bases chart values will be drawn
    private float[] yData = {50, 50, 50};
    //Private variable to store the label of the chart expenses
    private String[] xData = {"You", "John", "Jamie"};
    //Variable for the Pie Chart variable
    private PieChart mchart;
    private int groupID;
    private String groupName;
    ArrayList<ExpenseModel> expenseModel;
    public static String TAG = "PieChartExpense";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        expenseModel = new ArrayList<>();
        Intent intent = getIntent();
        groupID = intent.getIntExtra("groupid", 0);
        groupName = intent.getStringExtra("groupname");

        Log.e(TAG,"I am here");
        Log.e(TAG,String.valueOf(groupID));
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.getGroupExpense(groupID, new AsyncResponse() {
            @Override
            public void sendData(String data) {



                try {
                    JSONArray main = new JSONArray(data);


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

//                        if(item.optBoolean("groupName")){
//
//                        }

                        Log.e(TAG,"I am inside function");

                        expenseModel.add(new ExpenseModel(id, groupID, amount, date, category, description, groupName, userModel));



//                        Log.e("amount", String.valueOf(amount));

                    }

//                    for (ExpenseModel e : expenseModel) {
//                        Log.e(TAG, "username: "+e.getUsermodel().getUsername());
//                        Log.e(TAG, "expense: "+e.getAmount());
//                    }

                    expenseModel = processData(expenseModel);
                    displayPieChart(expenseModel);

//                    for (ExpenseModel e : expenseModel) {
//                        Log.e(TAG, "username: "+e.getUsermodel().getUsername());
//                        Log.e(TAG, "expense: "+e.getAmount());
//                    }

                } catch (Exception e) {
                           Log.e("error", "error", e);

                }


            }
        });


    }

    public void displayPieChart(ArrayList<ExpenseModel> expenseModel) {
        //Pie chart referring to the view
        mchart = (PieChart) findViewById(R.id.chart);
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
                if (e == null)
                    return;
                Toast.makeText(PieChartExpense.this, String.valueOf(e.getY()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        // Legend -creating of labels and headings for the Pie chart .
        Legend l = mchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(13f);
        l.setDrawInside(false);
        l.setYEntrySpace(2f);
        l.setYOffset(10f);

    }

    // Method to add Data into the Pie chart
    private void addData() {
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i <= expenseModel.size()-1; i++) {
            yVals.add(new PieEntry(expenseModel.get(i).getAmount(),expenseModel.get(i).getUsermodel().getUsername()));


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

    public ArrayList<ExpenseModel> processData(ArrayList<ExpenseModel> expenseModel) {

        for (int i = 0; i <= expenseModel.size() - 1; i++) {
            for (int j = i+1; j <= expenseModel.size() - 1; j++) {
                if (expenseModel.get(i).getUsermodel().getUser_id() == expenseModel.get(j).getUsermodel().getUser_id()) {
                    expenseModel.get(i).setAmount(expenseModel.get(i).getAmount()+expenseModel.get(j).getAmount());
                    expenseModel.remove(expenseModel.get(j));
                    j -=1;
                }
            }
        }

        return expenseModel;


    }
}
