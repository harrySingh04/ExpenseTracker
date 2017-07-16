package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.expensetracker.AsyncResponse;
import com.expensetracker.Dbutils.GroupInfo;
import com.expensetracker.Adapters.GroupAdapter;
import com.expensetracker.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.GroupModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupView extends AppCompatActivity {


    private RecyclerView group_container;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GroupModel> groupdetails = new ArrayList<GroupModel>();
    Context context;
    ItemClickListener itemClickListener;
    public static String TAG = "GroupView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_view);
        Log.e("in groupview", "in groupview");
        context = this;
        group_container = (RecyclerView) findViewById(R.id.single_group_container);
        layoutManager = new LinearLayoutManager(context);

        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {

                Log.e("in async response", data);

                try {
                    JSONArray main = new JSONArray(data);


                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);
                        String name = item.getString("name");
                        int group_id = item.getInt("group_id");
                        int userid = item.getInt("user_id");
                        Log.e("name", name);

                        groupdetails.add(new GroupModel(name, group_id, userid));
                    }

                    for (GroupModel g : groupdetails) {
                        Log.e("name of group", g.getName());
                    }


                    adapter = new GroupAdapter(groupdetails, itemClickListener);
                    layoutManager = new LinearLayoutManager(context);
                    group_container.setLayoutManager(layoutManager);
                    group_container.setHasFixedSize(true);
                    group_container.setAdapter(adapter);


                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        };

        GroupInfo groupInfo = new GroupInfo(asyncResponse);
        groupInfo.getAllGroupsForUser(1);


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {


                Log.e(TAG,String.valueOf(clickedItemIndex));
                Intent intent = new Intent();
                intent.setClass(context,SingleGroupDetails.class);
                intent.putExtra("groupid",clickedItemIndex);
                context.startActivity(intent);


            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_brown, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        MenuPane menupane = new MenuPane();
        menupane.menu(this, id);


        return super.onOptionsItemSelected(item);
    }

}
