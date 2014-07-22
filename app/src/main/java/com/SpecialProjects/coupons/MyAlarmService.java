package com.SpecialProjects.coupons;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.support.v4.app.NotificationCompat.Builder;


/**
 * Created by David Smith on 7/19/2014.
 */
public class MyAlarmService extends Service {

    public static final String TAG = "Schedule demo";
    private static final int NOTIFICATION_ID;

    static {
        NOTIFICATION_ID = 1;
    }

    private NotificationManager notificationManager;
    Builder builder;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

//    @Override
//    public void onStart(){
//
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Tag","onStart entered");
        super.onStartCommand(intent, flags, startId);

//        //NotificationCompat.
//        Builder builder = new Builder(this);
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        //set the intent that will fire when the user taps the notification
//        //TODO
//        builder.setAutoCancel(true);
//        //build the notifications appearance
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
//        builder.setContentTitle("Notification Test");
//        builder.setContentText("notification content text");
//        builder.setSubText("notification subText");
//
//        //send the notification
//        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID,builder.build());
        sendNotification();
        return Service.START_NOT_STICKY;
    }

    private void  sendNotification(){
        String txtTitle = "Alarm Title";
        String subText = "this is subText";
        String summaryText = "This is a summary";
        String msg = "This is a long message to give data to the user via notification Message";
        String tickerText = "this is ticker text";
        notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent dismissIntent = new Intent(this,Main.class);
       // dismissIntent.setAction();
        PendingIntent piDismiss = PendingIntent.getService(this,0,dismissIntent,0);

        PendingIntent contentIntent = PendingIntent.getActivity(this,0,new Intent(this,Main.class),0);

        Builder builder = new Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(txtTitle)
                .setSubText(subText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText(summaryText)
                        .bigText(msg))
                .addAction(R.drawable.ic_action_dismiss, getString(R.string.action_dismiss), piDismiss)
                .setTicker(tickerText)
                .setContentText(msg)
                .setAutoCancel(true);

        builder.setContentIntent(contentIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
