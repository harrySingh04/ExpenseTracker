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


    private ArrayList<UserModel> friendDetails;
    private ArrayList<UserModel> groupDetails;
    private ItemClickListener itemClickListener;
    public static final String TAG = "FriendsList Adapter";


    public FriendsListAdapter(ArrayList<UserModel> friendDetails, ArrayList<UserModel> groupDetails, ItemClickListener itemClickListener) {
        this.friendDetails = friendDetails;
        this.groupDetails = groupDetails;
        this.itemClickListener = itemClickListener;
    }

    public FriendsListAdapter(ArrayList<UserModel> friendDetails, ItemClickListener itemClickListener) {
        this.friendDetails = friendDetails;
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

        holder.friendDetails.setText(friendDetails.get(position).getUsername());
        holder.bind(holder, position);

        if(groupDetails !=null){
        holder.setChecked(holder, position);
        }

        holder.friendDetails.isChecked();


    }

    @Override
    public int getItemCount() {
        return friendDetails.size();
    }

    public class FriendsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox friendDetails;
        String username;
        int userID;
        String email;

        public FriendsListViewHolder(View itemView) {
            super(itemView);

            friendDetails = (CheckBox) itemView.findViewById(R.id.username);
            // itemView.setOnClickListener(this);
            friendDetails.setOnClickListener(this);
        }

        public void bind(FriendsListViewHolder holder, int pos) {
            username = FriendsListAdapter.this.friendDetails.get(pos).getUsername();
            userID = FriendsListAdapter.this.friendDetails.get(pos).getUser_id();
            email = FriendsListAdapter.this.friendDetails.get(pos).getEmail();
        }

        public void setChecked(FriendsListViewHolder holder, int pos) {

//            Log.e(TAG, String.valueOf(FriendsListAdapter.this.friendDetails.get(pos).getUsername()));
//            Log.e(TAG, String.valueOf(groupDetails.get(pos).getUsername()));


            for (UserModel u : groupDetails) {
                if (u.getUser_id() == FriendsListAdapter.this.friendDetails.get(pos).getUser_id()) {
                    friendDetails.setChecked(true);
                    itemClickListener.onItemClick(userID);
                }
            }
        }


        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(userID);

        }
    }


}