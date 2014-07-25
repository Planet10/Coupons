package com.SpecialProjects.coupons;

import android.util.Log;

/**
 * Created by David Smith on 7/25/2014.
 */
public class RestApi {
    public RestApi() {
    }

    public void callApi(){
        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        double lat = 30.48;
        double lng = -97.77;
        int radius = 5000;
        String urlString = apiUrl + lat + "," + lng + "&" + "radius=" + radius + "&" + "types=cafe" + "&key=" + "AIzaSyBjE1Q01JTmfuaRWBw1NOfmpUJ5iN8B1xg";
        Log.i("UrlString", "url :" + urlString);
        new CallLocationApi().execute(urlString);
    }
}
