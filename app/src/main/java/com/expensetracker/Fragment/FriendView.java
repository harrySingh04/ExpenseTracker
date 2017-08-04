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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expensetracker.Activities.AddFriend;
import com.expensetracker.Adapters.FriendsAdapter;
import com.expensetracker.Dbutils.FriendsInfo;
import com.expensetracker.Interfaces.AsyncResponse;
import com.expensetracker.Interfaces.ItemClickListener;
import com.expensetracker.Model.UserModel;
import com.expensetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class FriendView extends Fragment {

    private RecyclerView friendsView;
    private FriendsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserModel> userdetails = new ArrayList<UserModel>();
    Context context;
    ItemClickListener itemClickListener;
    FriendsInfo friendsInfo;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String navigationItems[];
    SharedPreferences sharedPreferences;
    public static String TAG = "GroupView";
    private ProgressBar progressBar;
    private TextView staticnofriendtext;

    public FriendView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        //setLeftPane();
        final FragmentActivity fragmentActivity = getActivity();

        friendsView = (RecyclerView) view.findViewById(R.id.friendsrecyclerview);
        layoutManager = new LinearLayoutManager(fragmentActivity);
        sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        staticnofriendtext = (TextView) view.findViewById(R.id.nofriendmessage);
        friendsInfo = new FriendsInfo();
        friendsInfo.getallfriends(sharedPreferences.getInt("userid", 0), new AsyncResponse() {
            @Override
            public void sendData(String data) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("in async response", data);

                try {
                    JSONArray main = new JSONArray(data);

                    if (main.length() != 0) {

                        for (int i = 0; i < main.length(); i++) {
                            JSONObject item = main.getJSONObject(i);

                            int id = item.getInt("id");
                            String name = item.getString("username");
                            String email = item.getString("email");

                            userdetails.add(new UserModel(id, name, email));
                        }

                        for (UserModel u : userdetails) {
                            Log.e(TAG, String.valueOf("username" + u.getUsername()));
                        }

                        adapter = new FriendsAdapter(userdetails, itemClickListener);
                        layoutManager = new LinearLayoutManager(context);
                        friendsView.setLayoutManager(layoutManager);
                        friendsView.setHasFixedSize(true);
                        friendsView.setAdapter(adapter);
                    } else {
                        staticnofriendtext.setVisibility(View.VISIBLE);
                    }

                    Log.e("this is trhe dta", data);
                } catch (Exception e) {
                    Log.e("oiasdha", "lskdkj", e);
                }
            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {

            }
        };

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent();
                intent.setClass(fragmentActivity, AddFriend.class);
                startActivity(intent);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int friendid = (int) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + friendid);
                SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
                final int userid = sharedPreferences.getInt("userid", 0);
                new FriendsInfo().deletefriend(userid, friendid, new AsyncResponse() {
                    @Override
                    public void sendData(String data) {
//                        Intent intent = new Intent();
//                        intent.setClass(context, Home.class);
//                        startActivity(intent);

                        for(UserModel u:userdetails){
                            if(u.getUser_id()==friendid){
                                userdetails.remove(u);
                                adapter.swapCursor(userdetails);
                                break;
                            }
                        }

                        if(userdetails.isEmpty()){

                            staticnofriendtext.setVisibility(View.VISIBLE);

                        }

                    }
                });




            }
        }).attachToRecyclerView(friendsView);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userdetails.clear();
    }
}
