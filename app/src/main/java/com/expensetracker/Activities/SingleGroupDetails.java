package com.expensetracker.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Adapters.SingleGroupMemberAdapter;
import com.expensetracker.AsyncResponse;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.ItemClickListener;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleGroupDetails extends AppCompatActivity {
    public static String TAG = "SingleGroupDetails";
    ArrayList<UserModel> usermodel;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Context context;
    ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group_details);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);
        usermodel = new ArrayList<UserModel>();
        context = this;


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {
                Log.e(TAG, data);

                try {
                    JSONArray main = new JSONArray(data);

//                    String name = main.getString("id");
//                    String id = main.getString("username");
//                    String email = main.getString("email");
                    //     JSONArray items = main.getJSONArray("");

                    Log.e("data", data);

                    for (int i = 0; i <= main.length()-1; i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        String username = item.getString("username");
                        String email = item.getString("email");


                        Log.e(TAG,"I am in for loop");
                        usermodel.add(new UserModel(id, username, email));

//                        for (UserModel u : usermodel) {
//                            Log.e(e.getDate(), e.getDate());
//                        }


                        adapter = new SingleGroupMemberAdapter(usermodel, itemClickListener);
                        layoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);


                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


            }
        };

        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {


            }
        };


        Bundle extras = getIntent().getExtras();
        int groupid = extras.getInt("groupid");


        GroupInfo groupInfo = new GroupInfo(asyncResponse);

        groupInfo.getgroupmembers(groupid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
