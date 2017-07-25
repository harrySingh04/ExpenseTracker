package com.expensetracker.Adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import java.util.ArrayList;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsListViewHolder> {


    private ArrayList<UserModel> userDetails;
    private ItemClickListener itemClickListener;
    public static final String TAG = "FriendsList Adapter";


    public FriendsListAdapter(ArrayList<UserModel> userDetails, ItemClickListener itemClickListener) {
        this.userDetails = userDetails;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public FriendsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend_list, parent, false);
        Log.e(TAG, "In onCreateViewHolder");
        FriendsListAdapter.FriendsListViewHolder recyclerViewHolder = new FriendsListAdapter.FriendsListViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(FriendsListViewHolder holder, int position) {

        holder.friendDetails.setText(userDetails.get(position).getUsername());
        holder.bind(holder, position);


    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    public class FriendsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox friendDetails;
        String username;
        int userID;
        String email;

        public FriendsListViewHolder(View itemView) {
            super(itemView);

            friendDetails = (CheckBox) itemView.findViewById(R.id.username);
       //     itemView.setOnClickListener(this);
            friendDetails.setOnClickListener(this);
        }

        public void bind(FriendsListViewHolder holder, int pos) {

            username = userDetails.get(pos).getUsername();
            userID = userDetails.get(pos).getUser_id();
            email = userDetails.get(pos).getEmail();


        }


        @Override
        public void onClick(View v) {
            
            itemClickListener.onItemClick(userID);

        }
    }


}
