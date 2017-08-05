package com.expensetracker.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;

import java.util.ArrayList;

/**
 * Created by user on 10-07-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private ArrayList<ExpenseModel> expenseDetails;

    private ExpenseData expenseData;
    public static final String TAG = "Expense Adapter";


    public ExpenseAdapter(ArrayList<ExpenseModel> expenseDetails, ExpenseData expenseData) {
        this.expenseDetails = expenseDetails;
        this.expenseData = expenseData;
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

      //  Log.e(TAG, "Groupname " + String.valueOf(expenseDetails.get(position).getGroupname()));

        //    holder.category.setContentDescription(String.valueOf(R.string.category));

        holder.amount.setText(String.valueOf(expenseDetails.get(position).getAmount()));
        holder.description.setText(expenseDetails.get(position).getDescription());
        holder.category.setText(expenseDetails.get(position).getCategory());
        holder.date.setText(expenseDetails.get(position).getDate());

        if(expenseDetails.get(position).getCategory().equals("Restaurant"))
            holder.categoryImageView.setImageResource(R.drawable.restaurant);
        else if (expenseDetails.get(position).getCategory().equals("Food"))
            holder.categoryImageView.setImageResource(R.drawable.food);
        else if (expenseDetails.get(position).getCategory().equals("Bills"))
            holder.categoryImageView.setImageResource(R.drawable.bill);
        else if (expenseDetails.get(position).getCategory().equals("Education"))
            holder.categoryImageView.setImageResource(R.drawable.education);
        else if (expenseDetails.get(position).getCategory().equals("Travelling"))
            holder.categoryImageView.setImageResource(R.drawable.travelling);
        else if (expenseDetails.get(position).getCategory().equals("gas"))
            holder.categoryImageView.setImageResource(R.drawable.gas);
        else if (expenseDetails.get(position).getCategory().equals("shopping"))
            holder.categoryImageView.setImageResource(R.drawable.shopping);
        else if (expenseDetails.get(position).getCategory().equals("Groceries"))
            holder.categoryImageView.setImageResource(R.drawable.grocercies);
        else if (expenseDetails.get(position).getCategory().equals("Other utilities"))
            holder.categoryImageView.setImageResource(R.drawable.utilities);



        if (expenseDetails.get(position).getUsermodel() != null) {
            //holder.username.setText(expenseDetails.get(position).getUsermodel().getUsername());
        }

        if(expenseDetails.get(position).getGroupModel()!=null){
            holder.groupname.setText(expenseDetails.get(position).getGroupModel().getName());
        }
        else{
            holder.groupname.setText(expenseDetails.get(position).getGroupname());
        }


        holder.Bind(holder, position);


        //     holder.category.setContentDescription(String.valueOf(R.string.category));

    }

    public void swapCursor( ArrayList<ExpenseModel> expenseDetails) {

        this. expenseDetails =  expenseDetails;
      //  if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
     //   }
    }

    @Override
    public int getItemCount() {
        return expenseDetails.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView amount, description, category, date, groupname, username;
        String totalDescription, categoryName, expenseDate, group;
        int id, totalAmount;
        ImageView categoryImageView;

        public ExpenseViewHolder(View view) {
            super(view);

//            Log.e(class_name, "RecyclerViewHolder");
            amount = (TextView) view.findViewById(R.id.amount);
            description = (TextView) view.findViewById(R.id.description);
            category = (TextView) view.findViewById(R.id.category);
            date = (TextView) view.findViewById((R.id.date));
            groupname = (TextView) view.findViewById((R.id.groupname));
          //  username = (TextView) view.findViewById((R.id.username));
            categoryImageView = (ImageView) view.findViewById(R.id.image_category);
            view.setOnClickListener(this);
//            groupname.setContentDescription(String.valueOf(R.string.group));
//            category.setContentDescription(String.valueOf(R.string.category));

        }

        public void Bind(ExpenseViewHolder holder, int position) {
            totalAmount = expenseDetails.get(position).getAmount();
            totalDescription = expenseDetails.get(position).getDescription();
            categoryName = expenseDetails.get(position).getCategory();
            expenseDate = expenseDetails.get(position).getDate();
            group = expenseDetails.get(position).getGroupname();
            id = expenseDetails.get(position).getId();
            holder.itemView.setTag(id);


        }


        @Override
        public void onClick(View v) {


            expenseData.expenseDetails(id, totalDescription, totalAmount, expenseDate, categoryName, group);
        }
    }


}