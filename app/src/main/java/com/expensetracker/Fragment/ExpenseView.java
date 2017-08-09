package com.expensetracker.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Activities.AddExpense;
import com.expensetracker.Activities.Updatexpense;
import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Dbutils.ExpenseInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseView extends Fragment {

    private RecyclerView expense_container;
    private ExpenseAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<ExpenseModel>();
    private Context context;
    private ProgressBar progressBar;
    ItemClickListener itemClickListener;
    ExpenseData expenseData;

    private FloatingActionButton button;
    SharedPreferences sharedPreferences;
    public static String TAG = "Expense View";
    TextView noExpenseStaticMessage;


    public ExpenseView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_expense_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getContext();
        final FragmentActivity fragmentActivity = getActivity();
        expense_container = (RecyclerView) view.findViewById(R.id.expense_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        expense_container.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expense_container.getContext(),
                layoutManager.getOrientation());
        expense_container.addItemDecoration(dividerItemDecoration);
        expense_container.setVisibility(View.VISIBLE);
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        noExpenseStaticMessage = (TextView) view.findViewById(R.id.noExpenseStaticMessage);
        //     String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        //setLeftPane();
        //  Log.e("token", refreshedToken);


        button = (FloatingActionButton) view.findViewById(R.id.AddExpense);
        // button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(fragmentActivity, AddExpense.class);
                startActivity(intent);
            }
        });


        //  UserInfo userInfo = new UserInfo(asyncResponse);
        final ExpenseInfo expenseInfo = new ExpenseInfo(context);
        expenseInfo.getallexpense(sharedPreferences.getInt("userid", 0), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                Log.e(TAG,"value of data"+data);

                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONArray main = new JSONArray(data);


                    if (main.length() > 0) {



                    Log.e(TAG, data);

                    for (int i = 0; i < main.length(); i++) {
                        JSONObject item = main.getJSONObject(i);

                        int id = item.getInt("id");
                        int groupid = item.getInt("groupID");
                        int amount = item.getInt("amount");
                        String date = item.getString("date");
                        String description = item.getString("description");
                        String category = item.getString("category");
                        String groupName = item.getString("groupName");
//                        if(item.optBoolean("groupName")){
//
//                        }

                        expenseModel.add(new ExpenseModel(id, amount, date, category, groupid, description, groupName));

//                        for (ExpenseModel e : expenseModel) {
//                            Log.e(e.getDate(), e.getDate());
//                        }

                        //   Log.e("amount", String.valueOf(amount));

                    }
                }
                    else{
                        noExpenseStaticMessage.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }

                adapter = new ExpenseAdapter(expenseModel, expenseData);
               // layoutManager = new LinearLayoutManager(fragmentActivity);
                //expense_container.setLayoutManager(layoutManager);
                expense_container.setHasFixedSize(true);
                expense_container.setAdapter(adapter);


            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {


            }
        };

        expenseData = new ExpenseData() {

            @Override
            public void expenseDetails(int id, String description, int amount, String date, String category, String groupName) {
                Intent intent = new Intent();
                intent.setClass(context, Updatexpense.class);

                intent.putExtra("id", id);
                intent.putExtra("description", description);
                intent.putExtra("date", date);
                intent.putExtra("category", category);
                intent.putExtra("groupName", groupName);
                intent.putExtra("amount", amount);

                Log.e(TAG, "id is" + id);
                startActivity(intent);

            }
        };


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int id = (int) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + id);


                new ExpenseInfo(context).deleteexpense(id, new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
                        for (ExpenseModel e : expenseModel) {
                            if (e.getId() == id) {
                                expenseModel.remove(e);
                                adapter.swapCursor(expenseModel);
                                break;
                            }
                        }

                        if(expenseModel.isEmpty()){
                            noExpenseStaticMessage.setVisibility(View.VISIBLE);
                        }


                    }
                });

            }
        }).attachToRecyclerView(expense_container);

    }





   /* @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        expenseModel.clear();
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }


}

