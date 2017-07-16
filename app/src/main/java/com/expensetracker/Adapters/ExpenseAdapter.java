package com.expensetracker.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensetracker.ItemClickListener;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;

import java.util.ArrayList;

/**
 * Created by user on 10-07-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private ArrayList<ExpenseModel> expenseDetails;
    private ItemClickListener itemClickListener;
    public static final String TAG = "Recycler Adapter";

    public ExpenseAdapter(ArrayList<ExpenseModel> expenseDetails, ItemClickListener itemClickListener) {
        this.expenseDetails = expenseDetails;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_expense, parent, false);
        Log.e(TAG, "In onCreateViewHolder");
        ExpenseViewHolder recyclerViewHolder = new ExpenseViewHolder(view);
        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {


        //  holder.amount.setText(expenseDetails.get(position).getDescription());

        Log.e(TAG, "Amount" + String.valueOf(expenseDetails.get(position).getAmount()));

        holder.category.setContentDescription(String.valueOf(R.string.category));

        holder.amount.setText(String.valueOf(expenseDetails.get(position).getAmount()));
        holder.description.setText(expenseDetails.get(position).getDescription());
        holder.category.setText(expenseDetails.get(position).getCategory());
        holder.date.setText(expenseDetails.get(position).getDate());
        holder.groupname.setText(expenseDetails.get(position).getGroupname());

        holder.category.setContentDescription(String.valueOf(R.string.category));

    }

    @Override
    public int getItemCount() {
        return expenseDetails.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView amount, description, category, date, groupname;

        public ExpenseViewHolder(View view) {
            super(view);

//            Log.e(class_name, "RecyclerViewHolder");
            amount = (TextView) view.findViewById(R.id.amount);
            description = (TextView) view.findViewById(R.id.description);
            category = (TextView) view.findViewById(R.id.category);
            date = (TextView) view.findViewById((R.id.date));
            groupname = (TextView) view.findViewById((R.id.groupname));

            groupname.setContentDescription(String.valueOf(R.string.group));
            category.setContentDescription(String.valueOf(R.string.category));

        }


        @Override
        public void onClick(View v) {

        }
    }


}
