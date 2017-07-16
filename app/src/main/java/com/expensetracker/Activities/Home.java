package com.expensetracker.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.expensetracker.AsyncResponse;
import com.expensetracker.Dbutils.ExpenseInfo;
import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    private RecyclerView expense_container;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<ExpenseModel>();
    private final Context context = this;
    private ProgressBar progressBar;
    ItemClickListener itemClickListener;
    public static String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        expense_container = (RecyclerView) findViewById(R.id.expense_container);

        //    ExpenseAdapter expenseAdapter = new ExpenseAdapter();


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("token", refreshedToken);

        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    JSONArray main = new JSONArray(data);

//                    String name = main.getString("id");
//                    String id = main.getString("username");
//                    String email = main.getString("email");
                    //     JSONArray items = main.getJSONArray("");

                    Log.e("data", data);

                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        int groupid = item.getInt("groupID");
                        int amount = item.getInt("amount");
                        String date = item.getString("date");
                        int userID = item.getInt("userID");
                        String description = item.getString("description");
                        String category = item.getString("category");

                        expenseModel.add(new ExpenseModel(id, amount, date, category, groupid, description));

                        for (ExpenseModel e : expenseModel) {
                            Log.e(e.getDate(), e.getDate());
                        }

                        Log.e("amount", String.valueOf(amount));

                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


                adapter = new ExpenseAdapter(expenseModel, itemClickListener);
                layoutManager = new LinearLayoutManager(context);
                expense_container.setLayoutManager(layoutManager);
                expense_container.setHasFixedSize(true);
                expense_container.setAdapter(adapter);
                Log.e("description", "hhhhhhhhhhh");

            }
        };

        //  UserInfo userInfo = new UserInfo(asyncResponse);
        ExpenseInfo expenseInfo = new ExpenseInfo(asyncResponse);
        expenseInfo.getallexpense(1);


//        Button group_view = (Button) findViewById(R.id.group_view);
//
//        group_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(context, GroupView.class);
//                startActivity(intent);
//            }
//        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {


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
