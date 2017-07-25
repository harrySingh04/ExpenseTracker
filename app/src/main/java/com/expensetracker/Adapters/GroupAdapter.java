package com.expensetracker.Adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.R;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {


    ArrayList<GroupModel> groupDetails = new ArrayList<>();
    public static final String class_name = "Recycler Adapter";
    ItemClickListener itemClickListener;
    private int groupid;
    public static String TAG= "groupView";

    //    public RecyclerAdapter(NewsDetails newsDetails) {
//
//    }
    public GroupAdapter(ArrayList<GroupModel> groupDetails, ItemClickListener itemClickListener) {
        this.groupDetails = groupDetails;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_group, parent, false);
        Log.e(class_name, "In onCreateViewHolder");
        GroupViewHolder groupViewHolder = new GroupViewHolder(view);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(GroupAdapter.GroupViewHolder holder, int position) {
        Log.e(class_name, "onBindViewHolder");
        holder.name.setText(groupDetails.get(position).getName());
        groupid = groupDetails.get(position).getGroup_id();
    }

    @Override
    public int getItemCount() {
        Log.e(class_name, "getItemCount");
        return groupDetails.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        String id;

        public GroupViewHolder(View view) {
            super(view);
            Log.e(class_name, "RecyclerViewHolder");
            name = (TextView) view.findViewById(R.id.group_name);
            view.setOnClickListener(this);
        }






        @Override
        public void onClick(View v) {
            Log.e(TAG,String.valueOf(groupid));
            itemClickListener.onItemClick(groupid);
        }
    }


}
