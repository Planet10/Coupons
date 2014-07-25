package com.SpecialProjects.coupons;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by David Smith on 7/22/2014.
 */
public class CouponLocations extends Service implements LocationListener {
    private LocationManager locationManager;
    Location location;
    double latitude;
    double longitude;
    double newLatitude;
    double newLongitude;
    private String provider;
    private Criteria criteria;
   // private final Context mContext;
    boolean isGpsEnabled = false;
    boolean isNetWorkEnabled = false;
    boolean canGetLocation = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1; // 1 minute
    MySQLiteHelper db;
    RestApi apiHelper;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("onStart", "Coupon onStart entered");
        getLocation();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public CouponLocations(){

    }
//    public CouponLocations(Context context){
//        this.mContext = context;
//
//        getLocation();
//    }

    private Location getLocation() {
        db = new MySQLiteHelper(this);
        apiHelper = new RestApi();
        try{
            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetWorkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGpsEnabled && !isNetWorkEnabled){
                //no network provider
                Log.i("noNetWork", "No Network");
            }else{
                this.canGetLocation = true;
                //get location from network
                if(isNetWorkEnabled){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,this
                    );
                    Log.d("NetWork", "NetWork");
                    if(locationManager !=null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location !=null){
                            latitude = location.getLatitude();
                            Log.i("latitude", " value = " + latitude);
                            longitude = location.getLongitude();
                            newLatitude = Math.round(latitude*1000.0)/1000.0;
                            newLongitude = Math.round(longitude*1000.0)/1000.0;
                            Log.i("newCoordinates", " Latitude: " + newLatitude + " " + "Longitude: " + newLongitude);
                            db.addCoordinates(new Coordinates(newLatitude,newLongitude));
                            db.openAndQueryDb();
                            db.listAllRecords();
                            apiHelper.callApi();
                        }
                    }
                }
                //get location from Gps
                if (isGpsEnabled){
                    if (location == null){
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BETWEEN_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,this
                        );
                        Log.d("Gps Enabled", "Gps Enabled");
                        if (locationManager !=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location !=null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                newLatitude = Math.round(latitude*1000.0)/1000.0;
                                newLongitude = Math.round(longitude*1000.0)/1000.0;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
