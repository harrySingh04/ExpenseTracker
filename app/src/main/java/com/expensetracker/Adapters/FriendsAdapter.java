package com.expensetracker.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import java.util.ArrayList;

/**
 * Created by user on 17-07-2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {


    private ArrayList<UserModel> userDetails;
    private ItemClickListener itemClickListener;
    public static final String TAG = "Recycler Adapter";


    public FriendsAdapter(ArrayList<UserModel> userDetails, ItemClickListener itemClickListener) {
        this.userDetails = userDetails;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend, parent, false);
        Log.e(TAG, "In onCreateViewHolder");
        FriendsAdapter.FriendsViewHolder recyclerViewHolder = new FriendsAdapter.FriendsViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {


        holder.name.setText(String.valueOf(userDetails.get(position).getUsername()));
        holder.email.setText(userDetails.get(position).getEmail());


        holder.Bind(holder,position);


    }

    @Override
    public int getItemCount()
    {
        return userDetails.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, email;
        private int userid;
        public FriendsViewHolder(View view) {
            super(view);
            Log.e(TAG, "RecyclerViewHolder");
            name = (TextView) view.findViewById(R.id.username);
            email = (TextView) view.findViewById(R.id.useremail);
            view.setOnClickListener(this);
        }

        public void Bind(FriendsViewHolder holder, int position){
            userid = (userDetails.get(position).getUser_id());
            holder.itemView.setTag(userid);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(userid);


        }
    }
}
