package com.expensetracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.expensetracker.Adapters.ExpenseAdapter;
import com.expensetracker.Fragment.ExpenseView;
import com.expensetracker.Fragment.FriendView;
import com.expensetracker.Fragment.GroupView;
import com.expensetracker.Interfaces.ExpenseData;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.MenuPane;
import com.expensetracker.Model.ExpenseModel;
import com.expensetracker.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    private RecyclerView expense_container;
    private ExpenseAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<ExpenseModel>();
    private final Context context = this;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setLeftPane();


        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        // Assign created adapter to viewPager
        viewPager.setAdapter(new ExpensePagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // This method setup all required method for TabLayout with Viewpager


        Intent intent = getIntent();
        int var = intent.getIntExtra("fragmentNumber",0);

        viewPager.setCurrentItem(var);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);



           /* expense_container = (RecyclerView) findViewById(R.id.expense_container);
            sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            progressBar = (ProgressBar)findViewById(R.id.progressbar) ;
            progressBar.setVisibility(View.VISIBLE);

          //  Log.e("token", refreshedToken);


            button = (FloatingActionButton) findViewById(R.id.AddExpense);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, AddExpense.class);
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
                    layoutManager = new LinearLayoutManager(context);
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
    */
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("drawercliock", String.valueOf(view.getId()));
            MenuPane.menu(context, position);
            // selectedItem();
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        //   Log.e("possssssssssssssss", String.valueOf(item));
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    public void setLeftPane() {


        //  mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);
//        setLeftPane();
        // set a custom shadow that overlays the main content when the drawer opens
        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //  getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
             //   Log.e(TAG, "ondrawer clossed");
                // getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            //    Log.e(TAG, "ondrawer opened");
                //   getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //     mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            //   selectItem(0);
//        }


    }


    public static class ExpensePagerAdapter extends FragmentPagerAdapter {
        // As we are implementing two tabs
        private static final int NUM_ITEMS = 3;
        private String[] tabTitle = {"Expense", "Groups", "Friends"};

        public ExpensePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // For each tab different fragment is returned
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ExpenseView();
                case 1:
                    return new GroupView();
                case 2:
                    return new FriendView();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            // For simplicity of this tutorial this string is hardcoded
            // Otherwise it should be access using string resource
            return tabTitle[position];
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}