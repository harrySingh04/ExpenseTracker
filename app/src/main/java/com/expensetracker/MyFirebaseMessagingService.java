package com.expensetracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.expensetracker.Activities.Home;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by user on 04-07-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static String TAG = "Firebase";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this);
        notificationbuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationbuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationbuilder.setAutoCancel(true);
        notificationbuilder.setColor(Color.BLUE);


        int resID = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());
//
//        Log.e(TAG, "name of image" + remoteMessage.getNotification().getIcon());
//        Log.e(TAG, "value of image int" + String.valueOf(resID));
//        Log.e(TAG, "value of inbuilt image" + String.valueOf(R.drawable.bill));

        notificationbuilder.setLargeIcon(BitmapFactory.decodeResource( getResources(),resID));
        notificationbuilder.setSmallIcon(resID);

        notificationbuilder.setContentIntent(pendingIntent);
        Log.d(TAG, "I am here");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notificationbuilder.build());
    }

}