package com.expensetracker.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Dbutils.FriendsInfo;
import com.expensetracker.Fragment.FriendView;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.MenuPane;
import com.expensetracker.R;

import static com.expensetracker.R.string.Add;

public class AddFriend extends AppCompatActivity {

    private EditText email;
    private Button addFriend;
    private FriendsInfo friendsInfo;
    private AsyncResponse asyncResponse;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    public static String TAG = "Add friend";
    Context context;
    SharedPreferences sharedPreferences;
    String username;
    Integer userid;
    ProgressBar progressBar;
    ListView listView;
    ArrayAdapter<String> listAdapter;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setLeftPane();

        email = (EditText) findViewById(R.id.useremail);
        addFriend = (Button) findViewById(R.id.add);
        context = this;
        sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userid = sharedPreferences.getInt("userid", 0);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        listView = (ListView) findViewById(R.id.contacts_list);
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }



      email.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {


              Log.e(TAG,"I am here");
              Log.e(TAG, email.getText().toString());

            Cursor cur =  ContactDetails(  email.getText().toString());

              Log.e(TAG,"size "+cur.getCount());
              listAdapter.clear();
              while(cur.moveToNext()){
                  Log.e(TAG,"I am here");

                  listAdapter.add(cur.getString(
                          cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
              }

              listView.setAdapter(listAdapter);

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {


              String val = ((TextView)arg1).getText().toString();

                Log.e(TAG,val);

                email.setText(val);

           //     Toast.makeText(SuggestionActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });




//        listAdapter.add("value1");
//        listAdapter.add("value2");


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e(TAG,String.valueOf(userid));
                progressBar.setVisibility(View.VISIBLE);
                friendsInfo.addfriend(userid, email.getText().toString(), username, asyncResponse = new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (data.isEmpty()) {

                            Log.e(TAG, "thisd is the data for add" + data);
                            Intent intent = new Intent();
                            intent.putExtra("fragmentNumber",2);
                            intent.setClass(context, Home.class);
                            startActivity(intent);

                        } else if (Integer.parseInt(data) == 1) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("Email ID does not exist with us. Please check the Email ID");
                            builder1.setCancelable(true);
                            builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }


                            });
                            builder1.create().show();
                        } else if (Integer.parseInt(data) == 2) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("The person is already in your Friend List");
                            builder1.setCancelable(true);
                            builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }


                            });
                            builder1.create().show();
                        }


                    }
                });


            }
        });


        friendsInfo = new FriendsInfo();


    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MenuPane.menu(context, position);
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


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void setLeftPane() {

        Log.e(TAG,"I am indside left pane");

        //  mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navigationItems = getResources().getStringArray(R.array.navigationItems);
//        setLeftPane();
        // set a custom shadow that overlays the main content when the drawer opens
        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_list_view, navigationItems));
        mDrawerList.setOnItemClickListener(new AddFriend.DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
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


    }


    public Cursor ContactDetails(String selectionCriteria) {

        String[] selectionArgs = { "%"+selectionCriteria+"%" };
        String columnName = ContactsContract.CommonDataKinds.Email.DATA +" LIKE ?";



         String[] PROJECTION = {

                // The contact's row id
                 ContactsContract.CommonDataKinds.Email.DATA,


               //  ContactsContract.CommonDataKinds.Phone.NUMBER,
                // ContactsContract.CommonDataKinds.Phone.
               //  ContactsContract.Contacts.DISPLAY_NAME,
            //     ContactsContract.Contacts._ID,
                 //ContactsContract.Contacts.




        };


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query( ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                PROJECTION, columnName,selectionArgs, null);


//        cr.
//        "select * from "+ ContactsContract.Contacts.CONTENT_URI +" where "+ ContactsContract.Contacts.DISPLAY_NAME + "LIKE"
//        + "%a%";
        ;
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    //Query phone here.  Covered next
//                }
//
//            }
//
//        }

        return cur;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
           //     Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}