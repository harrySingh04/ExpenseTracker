package com.expensetracker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by ishpr on 7/30/2017.
 */

public class PieChartAdapter extends RecyclerView.Adapter<PieChartAdapter.PieChartHolder>{



    private ArrayList<ExpenseModel>  pieDetails;

    Context context;

    public PieChartAdapter(Context context,ArrayList<ExpenseModel> pieDetails)
    {
        this.context = context;
        this.pieDetails = pieDetails;

    }
    @Override
    public PieChartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutIdList = LayoutInflater.from(context);
        int layoutIdForList = R.layout.activity_pie_recycler;
        boolean shouldAttachImmediately = false;

        View view = layoutIdList.inflate(layoutIdForList,parent,shouldAttachImmediately);
        PieChartHolder viewHolder = new PieChartHolder(view);
        return  viewHolder;

    }

    @Override
    public int getItemCount() {
        return pieDetails.size();
    }

    @Override
    public void onBindViewHolder(PieChartHolder holder, int position) {
       // super.onBindViewHolder(holder, position);


        holder.contactImage.setImageResource(R.drawable.images);
        holder.nameView.setText(pieDetails.get(position).getUserDetails().getUsername());
        holder.expenseView.setText(String.valueOf(pieDetails.get(position).getAmount()));

    }
    public void swapCursor( ArrayList<ExpenseModel> pieDetails) {

        this.pieDetails =  pieDetails;
        //  if (newCursor != null) {
        // Force the RecyclerView to refresh
        this.notifyDataSetChanged();
        //   }
    }

    public class PieChartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            ImageView contactImage;
            TextView nameView,expenseView;

        public PieChartHolder(View itemView) {
            super(itemView);
            contactImage =(ImageView) itemView.findViewById(R.id.image_pie);
            nameView = (TextView)itemView.findViewById(R.id.text_pie);
            expenseView = (TextView) itemView.findViewById(R.id.expense_pie);
        }

        @Override
        public void onClick(View v) {

        }

    }
}
