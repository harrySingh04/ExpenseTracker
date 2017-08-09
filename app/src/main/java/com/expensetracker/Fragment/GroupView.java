package com.expensetracker.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Activities.AddGroup;
import com.expensetracker.Activities.SingleGroupDetails;
import com.expensetracker.Adapters.GroupAdapter;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.GroupData;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupView extends Fragment {

    private RecyclerView group_container;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GroupModel> groupdetails = new ArrayList<GroupModel>();
    Context context;
    GroupData itemClickListener;
    public static String TAG = "GroupView";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    TextView nogrouptext;
    public GroupView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        context = getContext();
        group_container = (RecyclerView) view.findViewById(R.id.single_group_container);
        layoutManager = new LinearLayoutManager(context);
        sharedPreferences = context.getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar) ;
        progressBar.setVisibility(View.VISIBLE);
        nogrouptext = (TextView)view.findViewById(R.id.nogroupmessage);

        GroupInfo groupInfo = new GroupInfo(context);
        groupInfo.getAllGroupsForUser(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("in async response", data);

                try {
                    JSONArray main = new JSONArray(data);


                    if (main.length() != 0) {


                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);
                            String name = item.getString("name");
                            int group_id = item.getInt("group_id");
                            int userid = item.getInt("user_id");
                            Log.e("name", name);

                            groupdetails.add(new GroupModel(name, userid, group_id));
                        }

                        for (GroupModel g : groupdetails) {
                            Log.e("name of group", g.getName());
                        }


                        adapter = new GroupAdapter(groupdetails, new GroupData() {
                            @Override
                            public void groupDetails(int id, String groupName) {
                                //    Log.e(TAG,String.valueOf(clickedItemIndex));
                                Intent intent = new Intent();
                                intent.setClass(context, SingleGroupDetails.class);
                                intent.putExtra("groupid", id);
                                intent.putExtra("groupname", groupName);
                                context.startActivity(intent);

                            }
                        });
                        FragmentActivity fragmentActivity = getActivity();
                        layoutManager = new LinearLayoutManager(fragmentActivity);
                        group_container.setLayoutManager(layoutManager);
                        group_container.setHasFixedSize(true);
                        group_container.setAdapter(adapter);


                        Log.e("this is trhe dta", data);

                    }
                    else{
                        nogrouptext.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        });


        FloatingActionButton add_group = (FloatingActionButton) view.findViewById(R.id.add_group);


        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent();
                intent.setClass(context, AddGroup.class);
                startActivity(intent);


            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        groupdetails.clear();
    }
    }



