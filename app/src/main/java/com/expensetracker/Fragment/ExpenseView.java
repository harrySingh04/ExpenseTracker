package com.expensetracker.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.expensetracker.Activities.AddExpense;
import com.expensetracker.Activities.Home;
import com.expensetracker.Activities.Updatexpense;
import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Dbutils.ExpenseInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.id.toggle;
import static com.expensetracker.Activities.AddGroup.TAG;
import static com.expensetracker.R.array.navigationItems;
import static com.expensetracker.R.id.expense_container;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseView extends Fragment {

    private RecyclerView expense_container;
    private ExpenseAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<ExpenseModel>();
    private  Context context ;
    private ProgressBar progressBar;
    ItemClickListener itemClickListener;
    ExpenseData expenseData;

    private FloatingActionButton button;
    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    public static String TAG = "Home";


    public ExpenseView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_expense_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        context = getContext();
        final FragmentActivity fragmentActivity = getActivity();
        expense_container = (RecyclerView) view.findViewById(R.id.expense_container);
        expense_container.setVisibility(View.VISIBLE);
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar) ;

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
        final ExpenseInfo expenseInfo = new ExpenseInfo();
        expenseInfo.getallexpense(sharedPreferences.getInt("userid", 1), new AsyncResponse() {
            @Override
            public void sendData(String data) {

                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONArray main = new JSONArray(data);

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

                        for (ExpenseModel e : expenseModel) {
                            Log.e(e.getDate(), e.getDate());
                        }

                        Log.e("amount", String.valueOf(amount));

                    }

                } catch (Exception e) {
                    Log.e("error", "error", e);

                }


                adapter = new ExpenseAdapter(expenseModel, expenseData);
                layoutManager = new LinearLayoutManager(fragmentActivity);
                expense_container.setLayoutManager(layoutManager);
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


                new ExpenseInfo().deleteexpense(id, new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
//                        Intent intent = new Intent();
//                        intent.setClass(context, Home.class);
//                        startActivity(intent);
                        for(ExpenseModel e:expenseModel){
                            if(e.getId() == id){
                                expenseModel.remove(e);
                                adapter.swapCursor(expenseModel);
                            }
                        }


                    }
                });


            }
        }).attachToRecyclerView(expense_container);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context,position);
            // selectedItem();
        }
    }

    public void selectedItem(int position){

        switch(position){

            case 0:
                Log.e(TAG,"Item 1");
                break;

            case 1:
                Log.e(TAG,"Item 2");
                break;

            case 2:
                Log.e(TAG,"Item 3");
                break;

            case 3:
                Log.e(TAG,"Item 4");
                break;

        }
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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//
//    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        Log.e("possssssssssssssss", String.valueOf(item));
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }*/


  /*  public void setLeftPane() {


        //  mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(navigationItems);
//        setLeftPane();
        // set a custom shadow that overlays the main content when the drawer opens
        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
             /*   mDrawerLayout,         /* DrawerLayout object */
                /* nav drawer image to replace 'Up' caret */
               // R.string.drawer_open,  /* "open drawer" description for accessibility */
               // R.string.drawer_close  /* "close drawer" description for accessibility */
       /* ) {
            public void onDrawerClosed(View view) {
                Log.e(TAG, "ondrawer clossed");
                // getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                Log.e(TAG, "ondrawer opened");
                //   getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //     mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            //   selectItem(0);
//        }


    }

*/
}

