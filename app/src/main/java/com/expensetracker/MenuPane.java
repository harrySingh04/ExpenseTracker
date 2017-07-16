package com.expensetracker;

/**
 * Created by user on 15-07-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.expensetracker.Activities.GroupView;
import com.expensetracker.Activities.Home;

/**
 * Created by user on 10-12-2016.
 */

public class MenuPane {

    public void menu(final Context context, int id) {

        if (id == R.id.home) {
            Intent i = new Intent();
            i.setClass(context, Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

        if (id == R.id.group) {
            Intent i = new Intent();
            i.setClass(context, GroupView.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((Activity) (context)).finish();
        }

    }

}
