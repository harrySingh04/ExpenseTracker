package com.expensetracker;

/**
 * Created by user on 15-07-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.expensetracker.Activities.FriendsView;
import com.expensetracker.Activities.GroupView;
import com.expensetracker.Activities.Home;
import com.expensetracker.Activities.LoginUser;
import com.expensetracker.Activities.PieChartExpense;
import com.expensetracker.Activities.Summary;

/**
 * Created by user on 10-12-2016.
 */

public class MenuPane {

    public static void menu(final Context context, int id) {

        if (id == 0) {
            Intent i = new Intent();
            i.setClass(context, Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

        if (id ==1) {
            Intent i = new Intent();
            i.setClass(context, GroupView.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

        if (id == 2) {
            Intent i = new Intent();
            i.setClass(context, FriendsView.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

        if (id == 3) {
            Intent i = new Intent();
            i.setClass(context, Summary.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

        if (id == 4) {
            Intent i = new Intent();
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("data",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("userid",0);
            editor.putString("username","");
            editor.putString("email", "");

            i.setClass(context, LoginUser.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

    }

}
