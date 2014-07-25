package com.SpecialProjects.coupons;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by David Smith on 7/25/2014.
 */
public class CallLocationApi extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {

        //url to call
        String urlString = strings[0];
        String resultToDisplay = "";
        InputStream inputStream = null;

        //Http Get
        try{
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return resultToDisplay;
    }

}
