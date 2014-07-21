package com.SpecialProjects.coupons;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.support.v4.app.NotificationCompat.Builder;


/**
 * Created by David Smith on 7/19/2014.
 */
public class MyAlarmService extends Service implements LocationListener {

    public static final String TAG = "Schedule demo";
    private static final int NOTIFICATION_ID;

    static {
        NOTIFICATION_ID = 1;
    }

    private NotificationManager notificationManager;
    Builder builder;
    private LocationManager locationManager;
    private String provider;
    MySQLiteHelper db;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d("Tag","addLocation entered");
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        Location location = locationManager.getLastKnownLocation(provider);

        if(location !=null){
            db = new MySQLiteHelper(this);
            location = locationManager.getLastKnownLocation(provider);
            double lat;
            lat = location.getLatitude();
            double lng;
            lng = location.getLongitude();

            double newLat = Math.round(lat*1000.0)/1000.0;
            double newLng = Math.round(lng*1000.0)/1000.0;

            db.addCoordinates(new Coordinates(newLat, newLng));
            Log.i(TAG,newLat + " " + newLng);
        }else{
            Log.d("Tag","location is null");
        }
        // addLocation();
    }

//    @Override
//    public void onStart(){
//
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Tag","onStart entered");
        super.onStartCommand(intent, flags, startId);

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


    private void addLocation() {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location !=null){
            //TODO
            addLocation();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        //TODO
    }

    @Override
    public void onProviderDisabled(String s) {
        //TODO
    }
}
