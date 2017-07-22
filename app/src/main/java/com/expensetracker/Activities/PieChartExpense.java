package com.expensetracker.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

import java.util.ArrayList;

/**
 * Created by Harminder on 7/21/2017.
 */
// Creation of new Activity by Harminder to display Pie Chart for Expense Tracker
public class PieChartExpense extends AppCompatActivity {

//Private varibles yData to store the value of Expenses on which bases chart values will be drawn
    private float[] yData={10,37,50};
  //Private variable to store the label of the chart expenses
    private String[] xData={"You", "John","Jamie"};
  //Variable for the Pie Chart variable
    private PieChart mchart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        //Pie chart referring to the view
        mchart = (PieChart) findViewById(R.id.chart);
        //Apply Styling
        mchart.getDescription().setEnabled(false);
        mchart.setHoleRadius(54f);
        mchart.setTransparentCircleRadius(57f);
        mchart.setUsePercentValues(true);
        mchart.setCenterText("Expense Of This Month");
        //mchart.setExtraOffsets(55, 10, 10, 10);
        mchart.animateY(900);

        //Enable Rotation of the chart by touch
        mchart.setRotationAngle(0);
        mchart.setRotationEnabled(true);
        mchart.setEntryLabelColor(Color.BLACK);

        mchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e== null)
                    return;
                Toast.makeText(PieChartExpense.this,String.valueOf(e.getY()),Toast.LENGTH_SHORT).show();
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
    private void addData()
    {
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for(int i=0;i<yData.length;i++)
        {
            yVals.add(new PieEntry(yData[i],xData[i]));

        }

        //Create Pie Data Set
        PieDataSet dataSet = new PieDataSet(yVals, "Expense Tracker");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextSize(30f);

        //Add Many Colors

        ArrayList<Integer> colors = new ArrayList<Integer>();


        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for(int c: ColorTemplate.PASTEL_COLORS)
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
}
