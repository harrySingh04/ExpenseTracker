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
 * Created by user on 15-07-2017.
 */

public class SingleGroupMemberAdapter extends RecyclerView.Adapter<SingleGroupMemberAdapter.SingleMemberViewHolder> {

    private ArrayList<UserModel> userInfo;
    private ItemClickListener itemClickListener;
    public static final String TAG = "SingleGroupMember";

    public SingleGroupMemberAdapter(ArrayList<UserModel> userInfo, ItemClickListener itemClickListener) {
        this.userInfo = userInfo;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public SingleMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_group_member, parent, false);
        //   Log.e(TAG, "onCreateViewH");
        SingleMemberViewHolder recyclerViewHolder = new SingleMemberViewHolder(view);
        return recyclerViewHolder;

    }


    @Override
    public void onBindViewHolder(SingleMemberViewHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        Log.e(TAG,String.valueOf(userInfo.size()));
        return userInfo.size();

    }

    public class SingleMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int userid;
        String username;
        String email;
        TextView uame, user_email;

        public SingleMemberViewHolder(View view) {
            super(view);
            uame = (TextView) view.findViewById(R.id.username);
            user_email = (TextView) view.findViewById(R.id.useremail);
        }

        public void bind(SingleMemberViewHolder holder, int pos) {

            userid = userInfo.get(pos).getUser_id();
            email = userInfo.get(pos).getEmail();
            username = userInfo.get(pos).getUsername();
            uame.setText(username);
            user_email.setText(email);

        }


        @Override
        public void onClick(View v) {

        }
    }


}
